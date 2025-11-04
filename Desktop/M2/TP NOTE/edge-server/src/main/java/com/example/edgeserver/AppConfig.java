package com.example.edgeserver;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced // <-- Active le Load Balancer pour le WebClient
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}