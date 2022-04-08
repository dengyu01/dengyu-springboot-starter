package com.hsccc.myspringbootstarter.service;

import com.hsccc.myspringbootstarter.model.dto.AuthDto;
import com.hsccc.myspringbootstarter.model.dto.UserDetail;
import com.hsccc.myspringbootstarter.model.entity.User;
import com.hsccc.myspringbootstarter.model.query.LoginQuery;
import com.hsccc.myspringbootstarter.util.ServletUtil;
import com.hsccc.myspringbootstarter.util.ip.AddressUtil;
import com.hsccc.myspringbootstarter.util.ip.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final IUserService userService;

    public AuthService(AuthenticationManager authenticationManager,
                       TokenService tokenService, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    public AuthDto signToken(LoginQuery loginQuery) {
        // TODO: 验证码、用邮箱或者手机登录
        // TODO: 日志记录登录操作
        // 验证用户名和密码
        var authenticationToken =
                new UsernamePasswordAuthenticationToken(loginQuery.getUserName(), loginQuery.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetail userDetail = (UserDetail) authenticate.getPrincipal();

        // 在数据库保存用户当前登录ip等信息
        recordLoginInfo(userDetail.getId());
        setUserAgent(userDetail);
        String token = tokenService.createToken(userDetail);
        // 缓存token
        tokenService.refreshToken(userDetail);
        return new AuthDto(token);
    }

    public boolean logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        tokenService.deleteTokenFromCache(userDetail.getUuid());
        return true;
    }

    /**
     * 设置用户代理信息
     *
     * @param userDetail 用户登录信息
     */
    public void setUserAgent(UserDetail userDetail) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtil.getRequest().getHeader("User-Agent"));
        String ip = IpUtil.getIpAddr(ServletUtil.getRequest());
        userDetail.setIpaddr(ip);
        userDetail.setLoginLocation(AddressUtil.getRealAddressByIP(ip));
        userDetail.setBrowser(userAgent.getBrowser().getName());
        userDetail.setOs(userAgent.getOperatingSystem().getName());
    }

    public void recordLoginInfo(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setLoginIp(IpUtil.getIpAddr(ServletUtil.getRequest()));
        user.setLoginDate(LocalDateTime.now());
        userService.updateById(user);
    }

}
