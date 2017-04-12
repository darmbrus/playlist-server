package com.davidarmbrust.spi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * Provides access to current date function.
 */
@Configuration
public class DateConfig {

    @Bean
    public Date getCurrentDate() {
        return new Date();
    }
}
