package com.hsccc.myspringbootstarter.model.enums;

import com.hsccc.myspringbootstarter.core.common.Constant;

public enum ErrorInfo implements ValueEnum<String> {
    LOGIN_FAIL("A001", ApiStatus.UNAUTHORIZED, "账号或密码错误，请重新输入"),
    USERNAME_EXIST("A002", ApiStatus.BAD_REQUEST, "用户名已存在，请重新输入"),
    PHONE_OR_EMAIL_EXIST("A003", ApiStatus.BAD_REQUEST, "邮箱或手机号已存在，请重新输入"),
    AUTHENTICATION_ERROR("A004", ApiStatus.UNAUTHORIZED, "用户未登录，请登陆后操作"),
    NOT_FOUND_API("A005", ApiStatus.NOT_FOUND, Constant.BAD_REQUEST_USER_TIP),
    NOT_FOUND_REQUEST_METHOD("A006", ApiStatus.NOT_FOUND, Constant.BAD_REQUEST_USER_TIP),
    BAD_REQUEST_PARAM("A007", ApiStatus.BAD_REQUEST, Constant.BAD_REQUEST_USER_TIP),

    INTERNAL_SERVER_ERROR("B000", ApiStatus.INTERNAL_SERVER_ERROR, Constant.SERVER_ERROR_USER_TIP),
    DATABASE_ERROR("B100", ApiStatus.INTERNAL_SERVER_ERROR, Constant.SERVER_ERROR_USER_TIP),
    SERVICE_ERROR("B200", ApiStatus.INTERNAL_SERVER_ERROR, Constant.SERVER_ERROR_USER_TIP),
    NETWORK_ERROR("B300", ApiStatus.INTERNAL_SERVER_ERROR, Constant.SERVER_ERROR_USER_TIP)
    ;

    /**
     * 错误码为字符串类型，共 4 位，分成两个部分：错误产生来源+三位数字编号。
     * 错误产生来源分为 A/B/C，A 表示错误来源于用户；B 表示错误来源于当前系统；C 表示错误来源于第三方服务。
     */
    private final String errorCode;

    /**
     * 响应码
     */
    private final ApiStatus status;

    /**
     * 用户提示信息
     */
    private final String advice;

    ErrorInfo(String errorCode, ApiStatus status, String advice) {
        this.errorCode = errorCode;
        this.status = status;
        this.advice = advice;
    }

    @Override
    public String getValue() {
        return errorCode;
    }

    public ApiStatus getApiStatus() {
        return status;
    }

    public String getAdvice() {
        return advice;
    }
}
