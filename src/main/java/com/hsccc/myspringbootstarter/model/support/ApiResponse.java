package com.hsccc.myspringbootstarter.model.support;

import com.hsccc.myspringbootstarter.model.enums.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String path;
    private Integer code;
    private String msg;
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;

    public ApiResponse(ApiStatus status, T data) {
        this.code = status.getValue();
        this.msg = status.getMessage();
        this.data = data;
    }

    public ApiResponse(String path, ApiStatus status, T data) {
        this.path = path;
        this.code = status.getValue();
        this.msg = status.getMessage();
        this.data = data;
    }
}
