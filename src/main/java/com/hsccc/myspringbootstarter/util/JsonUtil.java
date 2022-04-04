package com.hsccc.myspringbootstarter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsccc.myspringbootstarter.exception.UtilException;

public class JsonUtil {
    public static String writeValueAsString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new UtilException.Builder(e.getMessage()).build();
        }
    }
}
