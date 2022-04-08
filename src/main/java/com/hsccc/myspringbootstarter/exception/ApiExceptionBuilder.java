package com.hsccc.myspringbootstarter.exception;

import com.hsccc.myspringbootstarter.model.enums.ErrorInfo;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Objects;

@Data
@Deprecated
public class ApiExceptionBuilder<T extends ApiException> {
    private String message;
    private ErrorInfo errorInfo;
    private Throwable throwable;

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
        if (Objects.isNull(this.message)) {
            this.message = throwable.getMessage();
        }
        return this;
    }

    public ApiExceptionBuilder<T> code(ErrorInfo errorCode) {
        this.errorInfo = errorCode;
        return this;
    }

    @SneakyThrows
    public T build() {
        return exceptionClass.getConstructor(ApiExceptionBuilder.class).newInstance(this);
    }
}
