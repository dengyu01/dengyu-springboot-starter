package com.hsccc.myspringbootstarter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myspringbootstarter.cache")
@Data
public class CacheProperties {
    private String tokenHeader = "Authorization";

    private String tokenSecret = "Eu5nK3gjUMPwCcmXc3eMUQT2G8VEu+numIt2ZPGbst4=";

    private int expireTime = 30;
}

