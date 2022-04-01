package com.hsccc.myspringbootstarter.core.handler;

import com.hsccc.myspringbootstarter.core.common.Constant;
import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.support.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    /**
     * 处理参数校验错误
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> errMap = new HashMap<>();
        fieldErrors.forEach(filedError -> errMap.put(filedError.getField(), filedError.getDefaultMessage()));
        return new ApiResponse<>(400, Constant.BAD_REQUEST_MSG, errMap);
    }

    /**
     * 处理 RequestBody 为空，或者请求参数格式错误导致无法解析的问题
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Object handleHttpMessageNotReadable() {
        return new ApiResponse<>(400, Constant.BAD_REQUEST_MSG);
    }

    /**
     * 处理不支持的请求方法
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Object handleRequestMethodNotSupported() {
        return new ApiResponse<>(400, Constant.ILLEGAL_REQUEST_MSG);
    }

    /**
     * 处理不支持的请求路径
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public Object handleNoHandlerFound() {
        return new ApiResponse<>(404, Constant.NOT_FOUND_MSG);
    }


    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public Object handleInternalAuthenticationServiceException(AuthenticationException e) {
        if (e.getCause() instanceof ApiException apiException) {
            return handleApiServerException(apiException);
        }
        return new ApiResponse<>(500, Map.of("message", e.getMessage()));
    }

    /**
     * 权限认证相关异常
     */
    @ExceptionHandler({AuthenticationException.class})
    public Object handleTokenException(AuthenticationException e) {
        return new ApiResponse<>(403, Map.of("message", e.getMessage()));
    }

    @ExceptionHandler({ApiException.class})
    public Object handleApiServerException(ApiException e) {
        log.error(e.getMessage(), e);
        HashMap<String, Object> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        if (!Objects.isNull(e.getErrorData())) {
            errorMap.put("errorData", e.getErrorData());
        }
        return new ApiResponse<>(e.getStatus().value(), errorMap);
    }

    @ExceptionHandler({Exception.class})
    public Object handleGlobalException(Exception e) {
        log.error(e.getMessage(), e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ApiResponse<>(status.value(), Constant.ERROR_MSG, e);
    }
}
