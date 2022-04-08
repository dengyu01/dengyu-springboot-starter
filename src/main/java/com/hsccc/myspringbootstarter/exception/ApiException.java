package com.hsccc.myspringbootstarter.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsccc.myspringbootstarter.model.enums.ErrorInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonSerialize(using = ApiExceptionSerializer.class)
public class ApiException extends RuntimeException {

    /**
     * 错误码
     */
    private final ErrorInfo errorInfo;

    /**
     * 堆栈信息
     */
    private String trace;

    public ApiException(String message, ErrorInfo errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }

    public ApiException(String message, Throwable throwable, ErrorInfo errorInfo) {
        super(message, throwable);
        this.errorInfo = errorInfo;
    }
}
