package com.hsccc.myspringbootstarter.exception;

public class BeanException extends ApiException {
    BeanException(ApiExceptionBuilder<?> builder) {
        super(builder);
    }

    public static class Builder extends ApiExceptionBuilder<BeanException> {
        public Builder(String message) {
            super(BeanException.class);
            message(message);
        }
    }
}
