package com.hsccc.myspringbootstarter.controller;

import com.hsccc.myspringbootstarter.model.dto.UserDto;
import com.hsccc.myspringbootstarter.service.impl.UserServiceImpl;
import com.hsccc.myspringbootstarter.util.MyBeanUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author hsccc
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable("id") String id) {
        return MyBeanUtil.convert(userService.getById(id), UserDto.class);
    }
}
