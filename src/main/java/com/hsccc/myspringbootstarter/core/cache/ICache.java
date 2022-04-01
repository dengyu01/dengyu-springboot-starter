package com.hsccc.myspringbootstarter.core.cache;

import com.hsccc.myspringbootstarter.core.common.Constant;

import java.util.concurrent.TimeUnit;

public interface ICache {
    /**
     * 缓存对象
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    void setObject(String key, Object value);

    /**
     * 缓存对象并设置过期时间
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    void setObject(String key, Object value, Integer timeout, TimeUnit timeUnit);

    /**
     * 设置有效时间
     * @param key 缓存的键值
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    boolean expire(String key, long timeout, TimeUnit unit);

    /**
     * 刷新键值为key的缓存
     * @param key 缓存的键值
     */
    void refresh(String key);

    /**
     * 获得缓存的基本对象。
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    Object getObject(String key);

    /**
     * 删除对象
     * @param key 缓存的键值
     */
    void deleteObject(String key);

    default void setObject(String key, Object value, Integer timeout) {
        setObject(key, value, timeout, TimeUnit.SECONDS);
    }

    default boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    default String getKey(String key) {
        return Constant.CACHE_PREFIX + key;
    }

}
