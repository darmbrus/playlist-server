package com.davidarmbrust.spi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2/27/2017.
 */
@Configuration
public class AppConfig {

    private static Logger logger = Logger.getLogger(AppConfig.class.getName());

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
        try {
            configuration.setDirectoryForTemplateLoading(new ClassPathResource("/templates").getFile());
            configurer.setConfiguration(configuration);
        } catch (IOException ex) {
            logger.fine("Could not find template directory.");
        }
        return configurer;
    }
}
