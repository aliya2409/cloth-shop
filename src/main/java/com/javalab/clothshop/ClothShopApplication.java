package com.javalab.clothshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClothShopApplication {

    private static final Logger log = LoggerFactory.getLogger(ClothShopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClothShopApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
