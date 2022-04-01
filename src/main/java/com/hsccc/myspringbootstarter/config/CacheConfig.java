package com.hsccc.myspringbootstarter.config;

import com.hsccc.myspringbootstarter.config.properties.CacheProperties;
import com.hsccc.myspringbootstarter.core.cache.ICache;
import com.hsccc.myspringbootstarter.core.cache.LocalICache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfig {
    @Bean
    @ConditionalOnProperty(
            prefix = "myspringbootstarter.cache",
            name = "mode",
            havingValue = "local",
            matchIfMissing = true
    )
    public ICache localCache(CacheProperties cacheProperties) {
        return new LocalICache(cacheProperties);
    }
}
