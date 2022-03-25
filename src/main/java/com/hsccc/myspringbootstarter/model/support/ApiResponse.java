package com.hsccc.myspringbootstarter.model.support;

import lombok.Data;

import java.util.Date;

@Data
public class ApiResponse<T> {
    String path;
    int code;
    String message;
    Long timestamp = new Date().getTime();
    T payload;


    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(String path, int code, String message) {
        this.path = path;
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code, String message, T payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
    }

    public ApiResponse(String path, int code, String message, T payload) {
        this.path = path;
        this.code = code;
        this.message = message;
        this.payload = payload;
    }
}
