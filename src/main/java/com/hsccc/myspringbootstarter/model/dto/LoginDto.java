package com.hsccc.myspringbootstarter.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginDto {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(max = 32, message = "用户名输入不能超过32个字符")
    private String userName;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
}
