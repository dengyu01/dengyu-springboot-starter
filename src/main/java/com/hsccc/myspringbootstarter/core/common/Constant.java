package com.hsccc.myspringbootstarter.core.common;

import java.io.File;

public class Constant {
    public static final String SUCCESS_MSG = "操作成功";
    public static final String FORBIDDEN_MSG = "权限不足";
    public static final String NOT_FOUND_MSG = "资源不存在";
    public static final String ERROR_MSG = "服务器内部异常";
    public static final String BAD_REQUEST_MSG = "非法请求，请检查参数";
    public static final String ILLEGAL_REQUEST_MSG = "非法请求";

    public static final String FILE_SEPARATOR = File.separator;

    public static final String CACHE_PREFIX = "MY_SPRINGBOOT_STARTER_";
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";
}
