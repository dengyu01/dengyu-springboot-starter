package com.hsccc.myspringbootstarter.model.enums;

import com.hsccc.myspringbootstarter.exception.ServiceException;
import org.springframework.util.Assert;

import java.util.stream.Stream;

public interface ValueEnum<T> {

    /**
     * Gets enum value.
     *
     * @return enum value
     */
    T getValue();

    /**
     * Converts enum value to corresponding enum.
     *
     * @param enumType enum type
     * @param value    enum value
     * @param <V>      value generic
     * @param <E>      enum generic
     * @return corresponding enum
     */
    static <V, E extends Enum<E> & ValueEnum<V>> E valueToEnum(Class<E> enumType, V value) {
        Assert.notNull(enumType, "enum type must not be null");
        Assert.notNull(value, "value must not be null");
        Assert.isTrue(enumType.isEnum(), "type must be an enum type");

        return Stream.of(enumType.getEnumConstants())
                .filter(item -> item.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new ServiceException.Builder("unknown enum value: " + value).build());
    }
}
