package com.hsccc.myspringbootstarter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    protected final Object errorData;
    protected final HttpStatus status;

    ApiException(ApiExceptionBuilder<?> builder) {
        super(builder.getMessage(), builder.getThrowable());
        this.errorData = builder.getErrorData();
        this.status = builder.getStatus();
    }

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.errorData = null;
    }
}
