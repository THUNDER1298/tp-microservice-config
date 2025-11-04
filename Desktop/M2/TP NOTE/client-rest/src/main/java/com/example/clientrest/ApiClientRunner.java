package com.example.clientrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiClientRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApiClientRunner.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(String... args) throws Exception {

        logger.info("*************************************");
        logger.info("DÉBUT DU TEST CLIENT (TÂCHE 8)");
        logger.info("*************************************");

        // On teste en mode Production (ports 9xxx)
        String gatewayUrl = "http://localhost:9000";

        // --- Test 1: GET (Rôle USER) ---
        try {
            logger.info("Test 1: GET /api/product-composite/1 (Rôle USER)...");
            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.set("username", "user");
            userHeaders.set("password", "user_pass");
            userHeaders.set("role", "USER");
            HttpEntity<String> userRequest = new HttpEntity<>(userHeaders);

            ResponseEntity<String> getResponse = restTemplate.exchange(
                gatewayUrl + "/api/product-composite/1", 
                HttpMethod.GET, 
                userRequest, 
                String.class
            );

            logger.info("RÉUSSITE (GET): Status " + getResponse.getStatusCode());
            logger.info("Réponse (GET): " + getResponse.getBody());

        } catch (Exception e) {
            logger.error("ÉCHEC (GET): " + e.getMessage());
        }

        logger.info("---");

        // --- Test 2: POST (Rôle ADMIN) ---
        try {
            logger.info("Test 2: POST /product-service/products (Rôle ADMIN)...");
            HttpHeaders adminHeaders = new HttpHeaders();
            adminHeaders.set("username", "admin");
            adminHeaders.set("password", "admin_pass");
            adminHeaders.set("role", "ADMIN");
            adminHeaders.setContentType(MediaType.APPLICATION_JSON);

            String jsonBody = "{\"name\": \"Produit créé par le Client REST\", \"weight\": 15}";
            HttpEntity<String> adminRequest = new HttpEntity<>(jsonBody, adminHeaders);

            ResponseEntity<String> postResponse = restTemplate.exchange(
                gatewayUrl + "/product-service/products", 
                HttpMethod.POST, 
                adminRequest, 
                String.class
            );

            logger.info("RÉUSSITE (POST): Status " + postResponse.getStatusCode());
            logger.info("Réponse (POST): " + postResponse.getBody());

        } catch (Exception e) {
            logger.error("ÉCHEC (POST): " + e.getMessage());
        }

        logger.info("*************************************");
        logger.info("FIN DU TEST CLIENT (TÂCHE 8)");
        logger.info("*************************************");
    }
}