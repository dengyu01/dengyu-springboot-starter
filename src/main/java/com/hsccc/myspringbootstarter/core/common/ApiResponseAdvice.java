package com.hsccc.myspringbootstarter.core.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsccc.myspringbootstarter.model.support.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.constraints.NotNull;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NotNull MethodParameter returnType, @NotNull Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  @NotNull MediaType selectedContentType,
                                  @NotNull Class selectedConverterType,
                                  ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response) {
        String path = request.getURI().getPath();
        HttpStatus code = HttpStatus.OK;
        if (body instanceof HttpStatus) {
            code = (HttpStatus) body;
            body = null;
        } else if (body == null) {
            code = HttpStatus.NOT_FOUND;
        } else if (body instanceof ApiResponse<?> res) {
            code = HttpStatus.valueOf(res.getCode());
            body = res.getData();
        }

        String message;
        switch (code) {
            case OK -> message = Constant.SUCCESS_MSG;
            case FORBIDDEN -> message = Constant.FORBIDDEN_MSG;
            case BAD_REQUEST -> message = Constant.BAD_REQUEST_MSG;
            case NOT_FOUND -> message = Constant.NOT_FOUND_MSG;
            default -> message = Constant.ERROR_MSG;
        }

        response.setStatusCode(HttpStatus.valueOf(code.value()));
        // TODO: 接口返回String时，返回ApiResponse会报错
        if (body instanceof String) {
            try {
                return new ObjectMapper()
                        .writeValueAsString(new ApiResponse<>(path, code.value(), message, (String) body));
            } catch (JsonProcessingException ignored) {
            }
            return body;
        }
        return new ApiResponse<>(path, code.value(), message, body);
    }
}
