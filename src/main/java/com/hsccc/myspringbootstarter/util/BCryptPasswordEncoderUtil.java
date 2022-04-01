package com.hsccc.myspringbootstarter.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class BCryptPasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456";
        String encode = encoder.encode(password);
        log.info("password:" + encode);
        log.info("match result: " + encoder.matches(password, encode));
    }
}
