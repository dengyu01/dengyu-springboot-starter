package com.hsccc.myspringbootstarter;

import com.hsccc.myspringbootstarter.controller.AuthController;
import com.hsccc.myspringbootstarter.controller.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
class MySpringbootStarterApplicationTests {
    @Autowired
    private AuthController authController;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(authController);
    }
}
