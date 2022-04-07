package com.hsccc.myspringbootstarter;

import com.hsccc.myspringbootstarter.controller.AuthController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MySpringbootStarterApplicationTests {
    @Autowired
    private AuthController authController;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(authController);
    }
}
