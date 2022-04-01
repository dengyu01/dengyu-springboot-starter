package com.hsccc.myspringbootstarter.autoconfigure.controlleradvice;

import com.hsccc.myspringbootstarter.config.properties.MySpringBootStarterProperties;
import com.hsccc.myspringbootstarter.core.common.ApiResponseAdvice;
import com.hsccc.myspringbootstarter.core.handler.ApiExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Configuration
@EnableConfigurationProperties(MySpringBootStarterProperties.class)
public class ControllerAdviceAutoConfiguration {

    @ConditionalOnMissingBean(ResponseBodyAdvice.class)
    @ConditionalOnProperty(
            prefix = "myspringbootstarter.autoconfigure",
            name = "response",
            havingValue = "true", matchIfMissing = true
    )
    @Import(ApiResponseAdvice.class)
    public static class ApiResponseAdviceAutoConfig {
    }

    @RestController
    @ConditionalOnProperty(
            prefix = "myspringbootstarter.autoconfigure",
            name = "exception",
            havingValue = "true", matchIfMissing = true
    )
    @Import(ApiExceptionHandler.class)
    public static class ApiExceptionHandlerAutoConfig {
    }
}
