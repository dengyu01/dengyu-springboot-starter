package com.hsccc.myspringbootstarter.exception;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

@Data
public class ApiExceptionBuilder<T extends ApiException> {
    private String message;
    private Throwable throwable;
    private Object errorData;
    private HttpStatus status;

    protected Class<T> exceptionClass;

    public ApiExceptionBuilder(Class<T> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public ApiExceptionBuilder<T> message(String message) {
        this.message = message;
        return this;
    }

    public ApiExceptionBuilder<T> throwable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public ApiExceptionBuilder<T> errorData(Object errorData) {
        this.errorData = errorData;
        return this;
    }

    public ApiExceptionBuilder<T> status(HttpStatus status) {
        this.status = status;
        return this;
    }

    @SneakyThrows
    public T build() {
        return exceptionClass.getConstructor(ApiExceptionBuilder.class).newInstance(this);
    }
}
