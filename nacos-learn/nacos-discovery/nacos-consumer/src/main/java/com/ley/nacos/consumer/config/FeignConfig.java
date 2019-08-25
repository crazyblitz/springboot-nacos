package com.ley.nacos.consumer.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level logLevel() {
        return Logger.Level.FULL;
    }
}
