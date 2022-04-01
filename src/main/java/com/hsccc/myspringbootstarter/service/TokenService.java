package com.hsccc.myspringbootstarter.service;

import com.hsccc.myspringbootstarter.config.properties.CacheProperties;
import com.hsccc.myspringbootstarter.core.cache.ICache;
import com.hsccc.myspringbootstarter.core.common.Constant;
import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.dto.UserDetail;
import com.hsccc.myspringbootstarter.util.uuid.IdUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class TokenService {
    private final ICache cache;

    private final SecretKey key;

    private final CacheProperties cacheProperties;

    public TokenService(ICache cache, CacheProperties cacheProperties) {
        this.cache = cache;
        this.cacheProperties = cacheProperties;
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(cacheProperties.getTokenSecret()));
    }

    public UserDetail verifyToken(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        String uuid;
        try {
            uuid = parseToken(token);
        } catch (Exception e) {
            throw new ApiException("无效的用户Token", HttpStatus.FORBIDDEN);
        }
        Object res = cache.getObject(getTokenCacheKey(uuid));
        if (Objects.isNull(res)) {
            throw new ApiException("用户Token已过期，请重新登录", HttpStatus.FORBIDDEN);
        }
        return (UserDetail) res;
    }

    public String getTokenCacheKey(String uuid) {
        return Constant.TOKEN_PREFIX + uuid;
    }

    /**
     * 刷新Token有效期
     *
     * @param userDetail 用户登录信息
     */
    public void refreshToken(UserDetail userDetail) {
        userDetail.setLoginTime(System.currentTimeMillis());
        userDetail.setExpireTime(userDetail.getLoginTime() + cacheProperties.getExpireTime());
        // 根据uuid将loginUser缓存
        String cacheKey = getTokenCacheKey(userDetail.getUuid());
        cache.setObject(cacheKey, userDetail);
    }

    public void deleteTokenFromCache(String uuid) {
        cache.deleteObject(getTokenCacheKey(uuid));
    }

    /**
     * 用jjwt创建Token令牌
     *
     * @param userDetail 当前登录用户的信息
     * @return token
     */
    public String createToken(UserDetail userDetail) {
        String uuid = IdUtil.fastUUID();
        userDetail.setUuid(uuid);

        return Jwts.builder()
                .setSubject(uuid)
                .signWith(key).compact();
    }

    /**
     *  用jjwt解析token
     * @param token token值
     * @return token中的uuid
     */
    private String parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request 请求参数
     * @return token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(cacheProperties.getTokenHeader());
        if (StringUtils.hasText(token) && token.startsWith(Constant.TOKEN_PREFIX)) {
            token = token.replace(Constant.TOKEN_PREFIX, "");
        }
        return token;
    }
}
