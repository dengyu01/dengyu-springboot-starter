package com.hsccc.myspringbootstarter.core.handler;

import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.enums.ErrorInfo;
import com.hsccc.myspringbootstarter.model.support.ApiResponse;
import com.hsccc.myspringbootstarter.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
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
        return handleApiServerException(new ApiException(errMap.toString(), e, ErrorInfo.BAD_REQUEST_PARAM));
    }

    /**
     * 处理 RequestBody 为空，或者请求参数格式错误导致无法解析的问题
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Object handleHttpMessageNotReadable(Exception e) {
        return baseHandle(e, ErrorInfo.BAD_REQUEST_PARAM);
    }

    /**
     * 处理不支持的请求方法
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Object handleRequestMethodNotSupported(Exception e) {
        return baseHandle(e, ErrorInfo.NOT_FOUND_REQUEST_METHOD);
    }

    /**
     * 处理不支持的请求路径
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public Object handleNoHandlerFound(Exception e) {
        return baseHandle(e, ErrorInfo.NOT_FOUND_API);
    }


    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public Object handleInternalAuthenticationServiceException(AuthenticationException e) {
        if (e.getCause() instanceof ApiException apiException) {
            return handleApiServerException(apiException);
        }
        return handleGlobalException(e);
    }

    /**
     * 权限认证相关异常
     */
    @ExceptionHandler({AuthenticationException.class})
    public Object handleTokenException(AuthenticationException e) {
        return baseHandle(e, ErrorInfo.AUTHENTICATION_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public Object handleGlobalException(Exception e) {
        return baseHandle(e, ErrorInfo.INTERNAL_SERVER_ERROR);
    }

    public ApiResponse<?> baseHandle(Exception e, ErrorInfo errorInfo) {
        return handleApiServerException(new ApiException(e.getMessage(), e, errorInfo));
    }

    @ExceptionHandler({ApiException.class})
    public ApiResponse<ApiException> handleApiServerException(ApiException e) {
        //TODO: only return exception stack trace in dev mode.
        if (!Objects.isNull(e.getCause())) {
            e.setTrace(ExceptionUtils.getStackTrace(e.getCause()));
        }
        return new ApiResponse<>(e.getErrorInfo().getApiStatus(), e);
    }
}
