package com.hsccc.myspringbootstarter.autoconfigure.security;

import com.hsccc.myspringbootstarter.config.CacheConfig;
import com.hsccc.myspringbootstarter.config.SecurityConfig;
import com.hsccc.myspringbootstarter.config.properties.CacheProperties;
import com.hsccc.myspringbootstarter.core.cache.ICache;
import com.hsccc.myspringbootstarter.core.handler.AccessDeniedHandlerImpl;
import com.hsccc.myspringbootstarter.core.handler.AuthenticationEntryPointImpl;
import com.hsccc.myspringbootstarter.filter.JwtAuthenticationFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(
    prefix = "myspringbootstarter.autoconfigure",
    name = "security",
    havingValue = "true", matchIfMissing = true
)
@MapperScan("com.hsccc.myspringbootstarter.mapper")
@ComponentScan(basePackages = {"com.hsccc.myspringbootstarter.service", "com.hsccc.myspringbootstarter.controller"})
public class SecurityAutoConfiguration {

    @ConditionalOnMissingBean(ICache.class)
    @Import(CacheConfig.class)
    static class CacheAutoConfiguration {
    }

    @ConditionalOnMissingBean(JwtAuthenticationFilter.class)
    @Import(JwtAuthenticationFilter.class)
    static class JwtFilterAutoConfiguration {
    }

    @ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
    @Import({SecurityConfig.class})
    static class SecurityConfigAutoConfiguration {
    }

    @ConditionalOnMissingBean(AccessDeniedHandler.class)
    @Import(AccessDeniedHandlerImpl.class)
    static class AccessDeniedHandlerAutoConfiguration {
    }

    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    @Import(AuthenticationEntryPointImpl.class)
    static class AuthenticationEntryPointAutoConfiguration {
    }

}
