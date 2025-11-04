package com.example.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer; // Importer
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // Importer

@SpringBootApplication
@EnableConfigServer // Active le serveur de configuration
@EnableDiscoveryClient // Pour qu'il s'enregistre à Eureka
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}