package com.hsccc.myspringbootstarter.model.dto;

import lombok.Data;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Data
public class UserDto {
    @Null
    private Long id;

    private String userName;

    private String nickName;

    private String password;

    @Null
    private String status;

    private String email;

    private String phoneNumber;

    private String sex;

    private String avatar;

    @Null
    private String userType;

    @Null
    private Long createBy;

    @Null
    private LocalDateTime createTime;

    @Null
    private Long updateBy;

    @Null
    private LocalDateTime updateTime;

    @Null
    private Integer delFlag;
}