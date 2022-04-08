package com.hsccc.myspringbootstarter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsccc.myspringbootstarter.exception.ServiceException;

public class JsonUtil {
    public static String writeValueAsString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new ServiceException.Builder(e.getMessage()).build();
        }
    }
}
