package com.hsccc.myspringbootstarter.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException(ApiExceptionBuilder<?> builder) {
        super(builder);
    }

    public static class Builder extends ApiExceptionBuilder<NotFoundException> {
        public Builder(String message) {
            super(NotFoundException.class);
            message(message);
            status(HttpStatus.NOT_FOUND);
        }
    }
}
