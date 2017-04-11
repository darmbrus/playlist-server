package com.davidarmbrust.spi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * Created by Administrator on 4/10/2017.
 */
@Configuration
public class DateConfig {

    @Bean
    public Date getCurrentDate() {
        return new Date();
    }
}
