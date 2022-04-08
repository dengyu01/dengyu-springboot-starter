package com.hsccc.myspringbootstarter.service;

import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.dto.AuthDto;
import com.hsccc.myspringbootstarter.model.dto.UserDetail;
import com.hsccc.myspringbootstarter.model.entity.User;
import com.hsccc.myspringbootstarter.model.enums.ErrorInfo;
import com.hsccc.myspringbootstarter.model.query.LoginQuery;
import com.hsccc.myspringbootstarter.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserServiceImpl userService;

    private LoginQuery loginQuery = new LoginQuery().setUserName("user1").setPassword("123456");
    private Authentication authenticationToken;
    private User user;
    private UserDetail userDetail;

    @BeforeEach
    void setUp() {
        authenticationToken =
                new UsernamePasswordAuthenticationToken(loginQuery.getUserName(), loginQuery.getPassword());
        user = new User()
                .setUserName(loginQuery.getUserName())
                .setPassword(loginQuery.getPassword()).setId(1L);
        userDetail = new UserDetail(user);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetail, null, null);
        given(authenticationManager.authenticate(authenticationToken)).willReturn(authentication);

        given(tokenService.createToken(userDetail)).willReturn("ok");
        given(userService.updateById(user)).willReturn(true);
    }

    @Test
    void signTokenWithOk() {
        AuthDto authDto = authService.signToken(loginQuery);
        assertEquals(authDto.getToken(), "ok");
    }

    @Test
    void signTokenWithError() {
        given(authenticationManager.authenticate(authenticationToken))
                .willThrow(new UsernameNotFoundException("用户名或密码输入错误"));
        assertThrows(AuthenticationException.class, () -> authService.signToken(loginQuery), "用户名或密码输入错误");
    }

    @Test
    void signTokenWithError2() {
        given(authenticationManager.authenticate(authenticationToken))
                .willThrow(new UsernameNotFoundException("用户未注册"));
        assertThrows(AuthenticationException.class, () -> authService.signToken(loginQuery), "用户未注册");
    }

    @Test
    void signTokenWithError3() {
        given(tokenService.createToken(userDetail))
                .willThrow(new ApiException("创建Token失败", ErrorInfo.INTERNAL_SERVER_ERROR));
        assertThrows(ApiException.class, () -> authService.signToken(loginQuery), "创建Token失败");
    }

    @Test
    void signTokenWithError4() {
        doThrow(new ApiException("缓存服务失效", ErrorInfo.INTERNAL_SERVER_ERROR))
                .when(tokenService).refreshToken(userDetail);
        assertThrows(ApiException.class, () -> authService.signToken(loginQuery), "缓存服务失效");
    }

}