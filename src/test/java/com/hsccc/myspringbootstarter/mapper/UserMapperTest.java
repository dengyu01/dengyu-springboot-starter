package com.hsccc.myspringbootstarter.mapper;

import com.hsccc.myspringbootstarter.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

//    @Test
    @Disabled
    void selectListTest() {
        List<User> users = userMapper.selectList(null);
        log.info(users.toString());
    }
}
