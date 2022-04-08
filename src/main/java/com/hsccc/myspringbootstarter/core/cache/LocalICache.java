package com.hsccc.myspringbootstarter.core.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hsccc.myspringbootstarter.config.properties.CacheProperties;
import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.enums.ErrorInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
public class LocalICache implements ICache {
    private CacheProperties cacheProperties;

    public LocalICache(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    private Cache<String, Object> cache;

    private Cache<String, Object> getCache() {
        if (cache == null) {
            synchronized (this) {
                if (cache == null) {
                    cache = CacheBuilder.newBuilder()
                            //TODO: 缓存数量有限制
                            .maximumSize(10000)
                            .expireAfterWrite(cacheProperties.getExpireTime(), TimeUnit.MINUTES)
                            .build();
                }
            }
        }
        return cache;
    }

    @Override
    public void setObject(String key, Object value) {
        getCache().put(getKey(key), value);
        if (getCache().getIfPresent(getKey(key)) == null) {
            throw new ApiException("缓存服务失效", ErrorInfo.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void setObject(String key, Object value, Integer timeout, TimeUnit timeUnit) {
        throw new UnsupportedOperationException("This operation is not supported in ConcurrentHashMap");
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException("This operation is not supported in ConcurrentHashMap");
    }

    @Override
    public void refresh(String key) {
        var cache = getCache();
        Object val = cache.getIfPresent(key);
        if (!Objects.isNull(val)) {
            cache.put(key, val);
        }
    }

    @Override
    public Object getObject(String key) {
        return getCache().getIfPresent(getKey(key));
    }

    @Override
    public void deleteObject(String key) {
        getCache().invalidate(getKey(key));
    }
}
