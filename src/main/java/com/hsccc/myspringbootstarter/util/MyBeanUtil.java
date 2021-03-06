package com.hsccc.myspringbootstarter.util;

import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import com.hsccc.myspringbootstarter.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class MyBeanUtil {
    public static <T> T convert(Object source, Class<T> targetClass) {
        Assert.notNull(source, "Source object must not be null");
        Assert.notNull(targetClass, "Target class must not be null");

        try {
            T targetInstance = targetClass.getDeclaredConstructor().newInstance();
            BeanUtil.copyProperties(source, targetInstance);
            return targetInstance;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException.Builder("Failed to new " + targetClass.getName()
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
            BeanUtil.copyProperties(source, target, getNullPropertyNames(source));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException.Builder("Failed to copy properties: " + e.getMessage()).build();
        }
    }

    /**
     *  Gets null names array of property.
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanDesc beanDesc = BeanUtil.getBeanDesc(source.getClass());
        Collection<PropDesc> props = beanDesc.getProps();

        var emptyNames = new HashSet<String>();
        for (PropDesc prop: props) {

            if (Objects.isNull(prop.getValue(source))) {
                emptyNames.add(prop.getFieldName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
