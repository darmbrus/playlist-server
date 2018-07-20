package com.davidarmbrust.spi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Provides configuration for general application beans.
 */
@Configuration
public class AppConfig {

    public static String APP_VERSION = "0.1.9";

    private static Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
