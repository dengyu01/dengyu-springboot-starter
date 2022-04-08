package com.hsccc.myspringbootstarter.core.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsccc.myspringbootstarter.model.enums.ApiStatus;
import com.hsccc.myspringbootstarter.model.enums.ValueEnum;
import com.hsccc.myspringbootstarter.model.support.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.constraints.NotNull;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NotNull MethodParameter returnType, @NotNull Class converterType) {
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  @NotNull MediaType selectedContentType,
                                  @NotNull Class selectedConverterType,
                                  ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response) {
        String path = request.getURI().getPath();
        ApiStatus code = ApiStatus.OK;
        if (body == null) {
            // TODO: test
            code = ApiStatus.NOT_FOUND;
        } else if (body instanceof ApiResponse<?> apiResponse) {
            code = ValueEnum.valueToEnum(ApiStatus.class, apiResponse.getCode());
            body = apiResponse.getData();
        }
        response.setStatusCode(HttpStatus.valueOf(code.getValue()));
        // TODO: 接口返回String时，不会返回ApiResponse
//        if (body instanceof String) {
//            try {
//                return new ObjectMapper()
//                        .writeValueAsString(new ApiResponse<>(path, code.value(), message, (String) body));
//            } catch (JsonProcessingException ignored) {
//            }
//            return body;
//        }
        return new ApiResponse<>(path, code, body);
    }
}
