package com.example.recommendationservice;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 1. IMPORTER LE LOGGER ET ENVIRONMENT
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    // 2. AJOUTER LE LOGGER ET ENVIRONMENT
    private static final Logger logger = LoggerFactory.getLogger(RecommendationController.class);
    @Autowired
    private Environment environment;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(
            @Valid @RequestBody Recommendation recommendation) {
        
        Recommendation savedReco = recommendationRepository.save(recommendation);
        return new ResponseEntity<>(savedReco, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Recommendation>> getRecommendationsByProductId(
            @RequestParam(value = "productId") Long productId) {
        
        // 3. AJOUTER LA LIGNE DE LOG
        String port = environment.getProperty("local.server.port");
        logger.info(">>>>>> [TACHE 4] Appel 'Recommendation' reçu sur le port : " + port + " <<<<<<");

        List<Recommendation> recommendations = recommendationRepository.findByProductId(productId);
        return ResponseEntity.ok(recommendations);
    }
}