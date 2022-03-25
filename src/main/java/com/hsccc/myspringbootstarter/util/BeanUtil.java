package com.hsccc.myspringbootstarter.util;

import com.hsccc.myspringbootstarter.exception.BeanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BeanUtil {
    public static <T> T convert(Object source, Class<T> targetClass) {
        Assert.notNull(source, "Source object must not be null");
        Assert.notNull(targetClass, "Target class must not be null");

        try {
            T targetInstance = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, targetInstance);
            return targetInstance;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BeanException.Builder("Failed to new " + targetClass.getName()
                    + " instance or copy properties: " + e.getMessage()).build();
        }
    }

    public static <T> List<T> convertInBatch(Collection<?> sources, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sources)) {
            return Collections.emptyList();
        }
        return sources.stream().map(source -> convert(source, targetClass)).collect(Collectors.toList());
    }

    /**
     * Update properties (non null).
     */
    public static void copyProperties(Object source, Object target) {
        Assert.notNull(source, "source object must not be null");
        Assert.notNull(target, "target object must not be null");

        try {
            BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BeanException.Builder("Failed to copy properties: " + e.getMessage()).build();
        }
    }

    /**
     *  Gets null names array of property.
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        var emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
