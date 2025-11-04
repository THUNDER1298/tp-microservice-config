package com.example.reviewservice;

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
@RequestMapping("/reviews")
public class ReviewController {

    // 2. AJOUTER LE LOGGER ET ENVIRONMENT
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    @Autowired
    private Environment environment;

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewRepository.save(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviewsByProductId(
            @RequestParam(value = "productId") Long productId) {
        
        // 3. AJOUTER LA LIGNE DE LOG
        String port = environment.getProperty("local.server.port");
        logger.info(">>>>>> [TACHE 4] Appel 'Review' reçu sur le port : " + port + " <<<<<<");

        List<Review> reviews = reviewRepository.findByProductId(productId);
        
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}