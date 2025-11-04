package com.example.productcompositeservice;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced // <-- C'est la ligne la plus importante !
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}