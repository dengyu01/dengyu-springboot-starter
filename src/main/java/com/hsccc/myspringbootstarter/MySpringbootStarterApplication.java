package com.hsccc.myspringbootstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@MapperScan("com.hsccc.myspringbootstarter.mapper")
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.hsccc.myspringbootstarter.autoconfigure.security.*")
})
public class MySpringbootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringbootStarterApplication.class, args);
    }

}
