package com.hsccc.myspringbootstarter.controller;

import com.hsccc.myspringbootstarter.base.ControllerBaseTest;
import com.hsccc.myspringbootstarter.core.common.ApiResponseAdvice;
import com.hsccc.myspringbootstarter.core.handler.ApiExceptionHandler;
import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.dto.AuthDto;
import com.hsccc.myspringbootstarter.model.dto.LoginDto;
import com.hsccc.myspringbootstarter.model.support.ApiResponse;
import com.hsccc.myspringbootstarter.service.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


class AuthControllerTest extends ControllerBaseTest {
    @MockBean
    private AuthService authService;
    @MockBean
    private ApiResponseAdvice apiResponseAdvice;
//    @MockBean
//    private ApiExceptionHandler apiExceptionHandler;
//    @Autowired
//    TestRestTemplate restTemplate;

    private static LoginDto loginDto;


    private static final String loginUrl = "/auth/token";

//    @Autowired
//    MockMvcProxy mvc;

    @BeforeAll
    static void setUp() {
        loginDto = new LoginDto().setUserName("user1").setPassword("123456");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void signTokenWithOk() throws Exception {
        AuthDto okToken = new AuthDto("ok");
        given(this.authService.signToken(loginDto)).willReturn(okToken);

        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody(AuthDto.class).isEqualTo(okToken);
    }

    @Test
    void signTokenWithApiExceptionError() {
        given(this.authService.signToken(loginDto)).willThrow(new ApiException("用户未注册", HttpStatus.FORBIDDEN));
        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody()
                .jsonPath("$.data.message")
                .isEqualTo("用户未注册");
    }

    @Test
    void signTokenWithApiExceptionError2() {
        given(this.authService.signToken(loginDto)).willThrow(new UsernameNotFoundException("用户名或密码输入错误"));
        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody()
                .jsonPath("$.data.message")
                .isEqualTo("用户名或密码输入错误");
    }

    @Test
    void signTokenWithApiExceptionError3() {
        given(this.authService.signToken(loginDto))
                .willThrow(new ApiException("缓存服务失效", HttpStatus.INTERNAL_SERVER_ERROR));
        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody()
                .jsonPath("$.code").isEqualTo("500")
                .jsonPath("$.data.message").isEqualTo("缓存服务失效");
    }

    @Test
    void signTokenWithEmptyUsername() {
        LoginDto loginDto = new LoginDto().setUserName("").setPassword("123456");
        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody()
                .jsonPath("$.data.userName")
                .isEqualTo("用户名不能为空");
    }

    @Test
    void signTokenWithMaxLengthUsername() {
        LoginDto loginDto = new LoginDto()
                .setUserName("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .setPassword("123456");
        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody()
                .jsonPath("$.data.userName")
                .isEqualTo("用户名输入不能超过32个字符");

        // boundary test
        loginDto.setUserName("000000000000000000000000000000001");
        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody()
                .jsonPath("$.data.userName")
                .isEqualTo("用户名输入不能超过32个字符");
        loginDto.setUserName("00000000000000000000000000000000");
        AuthDto okToken = new AuthDto("ok");
        given(this.authService.signToken(loginDto)).willReturn(okToken);
        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody(AuthDto.class).isEqualTo(okToken);
    }

    @Test
    void signTokenWithEmptyPassword() {
        LoginDto loginDto = new LoginDto().setUserName("user1").setPassword(null);
        postWithOk(loginUrl, loginDto, LoginDto.class)
                .expectBody()
                .jsonPath("$.data.password")
                .isEqualTo("密码不能为空");
    }

    //    @Test
//    void signToken2() throws Exception {
//        AuthDto testToken = new AuthDto("test");
//        given(this.authService.signToken(loginDto)).willReturn(testToken);
//        AuthDto object = restTemplate.postForObject(loginUrl, loginDto, AuthDto.class);
////        AuthDto authDto = (AuthDto) Objects.requireNonNull(object.get());
//        assertEquals(object.getToken(), testToken.getToken());
//        System.out.println(object);
////
////        ResultActions post = mvc.post(loginUrl, loginDto);
////        post.andExpect(status().isOk());
//    }

    @Test
    void logout() {
    }
}