package com.hsccc.myspringbootstarter.exception;

import org.springframework.http.HttpStatus;

public class UtilException extends ApiException {
    UtilException(ApiExceptionBuilder<?> builder) {
        super(builder);
    }

    public static class Builder extends ApiExceptionBuilder<UtilException> {
        public Builder(String message) {
            super(UtilException.class);
            message(message);
            status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
