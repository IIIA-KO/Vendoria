package com.vendoria.bff.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendoria.bff.feign.decoder.CustomFeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public CustomFeignErrorDecoder customFeignErrorResponseDecoder(ObjectMapper objectMapper) {
        return new CustomFeignErrorDecoder(objectMapper);
    }
}