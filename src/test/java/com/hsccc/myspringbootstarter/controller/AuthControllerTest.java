package com.hsccc.myspringbootstarter.controller;

import com.hsccc.myspringbootstarter.base.ControllerBaseTest;
import com.hsccc.myspringbootstarter.core.common.ApiResponseAdvice;
import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.dto.AuthDto;
import com.hsccc.myspringbootstarter.model.enums.ErrorInfo;
import com.hsccc.myspringbootstarter.model.query.LoginQuery;
import com.hsccc.myspringbootstarter.service.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.mockito.BDDMockito.given;


class AuthControllerTest extends ControllerBaseTest {
    @MockBean
    private AuthService authService;
    @MockBean
    private ApiResponseAdvice apiResponseAdvice;

    private static LoginQuery loginQuery;


    private static final String loginUrl = "/auth/token";

    @BeforeAll
    static void setUp() {
        loginQuery = new LoginQuery().setUserName("user1").setPassword("123456");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void signTokenWithOk() throws Exception {
        AuthDto okToken = new AuthDto("ok");
        given(this.authService.signToken(loginQuery)).willReturn(okToken);

        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody(AuthDto.class).isEqualTo(okToken);
    }

    @Test
    void signTokenWithApiExceptionError() {
        given(this.authService.signToken(loginQuery))
                .willThrow(new ApiException("用户未注册", ErrorInfo.AUTHENTICATION_ERROR));
        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody()
                .jsonPath("$.data.error_message")
                .isEqualTo("用户未注册");
    }

    @Test
    void signTokenWithApiExceptionError2() {
        given(this.authService.signToken(loginQuery)).willThrow(new UsernameNotFoundException("用户名或密码输入错误"));
        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody()
                .jsonPath("$.data.error_message")
                .isEqualTo("用户名或密码输入错误");
    }

    @Test
    void signTokenWithApiExceptionError3() {
        given(this.authService.signToken(loginQuery))
                .willThrow(new ApiException("缓存服务失效", ErrorInfo.INTERNAL_SERVER_ERROR));
        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody()
                .jsonPath("$.code").isEqualTo("500")
                .jsonPath("$.data.error_message").isEqualTo("缓存服务失效");
    }

    @Test
    void signTokenWithEmptyUsername() {
        LoginQuery loginQuery = new LoginQuery().setUserName("").setPassword("123456");
        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody()
                .jsonPath("$.data.error_message")
                .isEqualTo("{userName=用户名不能为空}");
    }

    @Test
    void signTokenWithMaxLengthUsername() {
        LoginQuery loginQuery = new LoginQuery()
                .setUserName("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .setPassword("123456");
        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody()
                .jsonPath("$.data.error_message")
                .isEqualTo("{userName=用户名输入不能超过32个字符}");

        // boundary test
        loginQuery.setUserName("000000000000000000000000000000001");
        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody()
                .jsonPath("$.data.error_message")
                .isEqualTo("{userName=用户名输入不能超过32个字符}");
        loginQuery.setUserName("00000000000000000000000000000000");
        AuthDto okToken = new AuthDto("ok");
        given(this.authService.signToken(loginQuery)).willReturn(okToken);
        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody(AuthDto.class).isEqualTo(okToken);
    }

    @Test
    void signTokenWithEmptyPassword() {
        LoginQuery loginQuery = new LoginQuery().setUserName("user1").setPassword(null);
        postWithOk(loginUrl, loginQuery, LoginQuery.class)
                .expectBody()
                .jsonPath("$.data.error_message")
                .isEqualTo("{password=密码不能为空}");
    }

    @Test
    void logout() {
    }
}