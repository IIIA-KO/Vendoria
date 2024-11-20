package com.vendoria.bff.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendoria.bff.feign.decoder.CustomFeignErrorDecoder;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public CustomFeignErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return new CustomFeignErrorDecoder(objectMapper);
    }

    @Bean
    public Feign.Builder feignBuilder(CustomFeignErrorDecoder errorDecoder) {
        return Feign.builder()
                .client(new ApacheHttpClient())
                .errorDecoder(errorDecoder);
    }
}