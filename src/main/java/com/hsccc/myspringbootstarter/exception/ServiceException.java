package com.hsccc.myspringbootstarter.exception;

import com.hsccc.myspringbootstarter.model.enums.ErrorInfo;

public class ServiceException extends ApiException {

    public ServiceException(String message, ErrorInfo errorInfo) {
        super(message, errorInfo);
    }

    public ServiceException(String message, Throwable throwable, ErrorInfo errorInfo) {
        super(message, throwable, errorInfo);
    }

    public static class Builder extends ApiExceptionBuilder<ServiceException> {
        public Builder(String message) {
            super(ServiceException.class);
            message(message);
            code(ErrorInfo.SERVICE_ERROR);
        }
    }
}
