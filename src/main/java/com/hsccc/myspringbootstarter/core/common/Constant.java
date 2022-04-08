package com.hsccc.myspringbootstarter.core.common;

import java.io.File;

public class Constant {
    public static final String SERVER_ERROR_USER_TIP = "对不起，系统开小差了，请稍后再试";
    public static final String BAD_REQUEST_USER_TIP = "请求有误, 请检查参数";

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
