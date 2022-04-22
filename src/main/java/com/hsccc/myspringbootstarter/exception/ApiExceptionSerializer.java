package com.hsccc.myspringbootstarter.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hsccc.myspringbootstarter.model.enums.ErrorInfo;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ApiExceptionSerializer extends JsonSerializer<ApiException> {
    @Override
    public void serialize(ApiException apiException, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        //TODO: refactor
        Map<String, Object> map = new LinkedHashMap<>();
        ErrorInfo errorInfo = apiException.getErrorInfo();
        map.put("error_code", errorInfo.getValue());
        map.put("error_message", apiException.getMessage());
        map.put("advice", errorInfo.getAdvice());
        if (!Objects.isNull(apiException.getTrace())) {
            map.put("trace", apiException.getTrace());
        }
        jsonGenerator.writeObject(map);
    }
}