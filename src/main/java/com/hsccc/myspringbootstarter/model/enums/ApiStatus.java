package com.hsccc.myspringbootstarter.model.enums;

public enum ApiStatus implements ValueEnum<Integer> {
    OK(200, "操作成功"),
    BAD_REQUEST(400, "无效请求"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "未授权"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部异常");

    private final int code;
    private final String message;

    ApiStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public Integer getValue() {
        return code;
    }
}
