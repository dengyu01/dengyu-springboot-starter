package com.hsccc.myspringbootstarter.filter;

import com.hsccc.myspringbootstarter.base.ControllerBaseTest;
import com.hsccc.myspringbootstarter.core.cache.ICache;
import com.hsccc.myspringbootstarter.core.common.Constant;
import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.dto.LoginDto;
import com.hsccc.myspringbootstarter.model.dto.UserDetail;
import com.hsccc.myspringbootstarter.model.entity.User;
import com.hsccc.myspringbootstarter.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class JwtAuthenticationFilterTest extends ControllerBaseTest {

    private final String token =
            Constant.TOKEN_PREFIX + " eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkMjdiMDljNS1mMjI4LTRlNDMtODkzMS0wMmYwN2U4OTI3YTA" +
                    "ifQ.eg3Zwzp44AeI0IGnAnBfpIFgz6PFHzgGQ9ngyCcjIYc";

    private LoginDto loginDto = new LoginDto().setUserName("user1").setPassword("123456");
    private Authentication authenticationToken;
    private User user;
    private UserDetail userDetail;

    @Value("${myspringbootstarter.cache.token-header}")
    private String tokenHeader;
    private String tokenKey = "Bearer d27b09c5-f228-4e43-8931-02f07e8927a0";

    private static final String loginOutUrl = "/auth/logout";

    @MockBean
    private ICache iCache;

    @BeforeEach
    void setUp() {
        authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword());
        user = new User()
                .setUserName(loginDto.getUserName())
                .setPassword(loginDto.getPassword()).setId(1L);
        userDetail = new UserDetail(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, null);
        given(iCache.getObject(tokenKey)).willReturn(userDetail);
    }

    @Test
    void doFilterInternalWithOk() {
        postBeforeExchange(loginOutUrl, tokenHeader, token).exchange().expectStatus().isOk();
    }

    @Test
    void doFilterInternalWithEmptyToken() {
        postBeforeExchange(loginOutUrl, null)
                .exchange().expectStatus().isForbidden()
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo("权限不足");
    }

    @Test
    void doFilterInternalWithError() {
        postBeforeExchange(loginOutUrl, tokenHeader, "error_token")
                .exchange().expectStatus().isForbidden()
                .expectBody()
                .jsonPath("$.data.message")
                .isEqualTo("无效的用户Token");
        given(iCache.getObject(tokenKey)).willReturn(null);
        postBeforeExchange(loginOutUrl, tokenHeader, token)
                .exchange().expectStatus().isForbidden()
                .expectBody()
                .jsonPath("$.data.message")
                .isEqualTo("用户Token已过期，请重新登录");
        given(iCache.getObject(tokenKey)).willThrow(new ApiException("缓存服务失效", HttpStatus.INTERNAL_SERVER_ERROR));
        postBeforeExchange(loginOutUrl, tokenHeader, token)
                .exchange().expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.data.message")
                .isEqualTo("缓存服务失效");
    }
}