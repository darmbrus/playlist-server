package com.davidarmbrust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:/secure.properties")
public class SpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiApplication.class, args);
	}
}
