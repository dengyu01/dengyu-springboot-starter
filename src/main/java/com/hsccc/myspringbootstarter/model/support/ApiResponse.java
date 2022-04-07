package com.hsccc.myspringbootstarter.model.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    // TODO: impl a builder
    String path;
    int code;
    String message;
    Long timestamp = new Date().getTime();
    T data;


    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(String path, int code, String message) {
        this.path = path;
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String path, int code, String message, T data) {
        this.path = path;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
