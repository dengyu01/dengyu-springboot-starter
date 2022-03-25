package com.hsccc.myspringbootstarter.common;

import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.support.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public Object handleApiServerException(ApiException e) {
        log.error(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
        return new ApiResponse<>(e.getStatus().value(), e.getMessage(), e.getErrorData());
    }

    @ExceptionHandler({Exception.class})
    public Object handleGlobalException(Exception e) {
        e.printStackTrace();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ApiResponse<>(status.value(), Constant.errorMsg, e);
    }
}
