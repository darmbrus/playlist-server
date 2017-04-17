package com.davidarmbrust.spi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Provides configuration for general application beans.
 */
@Configuration
public class AppConfig {

    public static String APP_VERSION = "0.1.4";

    private static Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration();
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        configurer.setConfiguration(configuration);
        return configurer;
    }
}
