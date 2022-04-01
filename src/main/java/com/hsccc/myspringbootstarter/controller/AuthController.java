package com.hsccc.myspringbootstarter.controller;

import com.hsccc.myspringbootstarter.model.dto.AuthDto;
import com.hsccc.myspringbootstarter.model.dto.LoginDto;
import com.hsccc.myspringbootstarter.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("token")
    public AuthDto signToken(@RequestBody LoginDto userParam) {
        return authService.signToken(userParam);
    }

    @PostMapping("logout")
    public boolean logout() {
        return authService.logout();
    }
}
