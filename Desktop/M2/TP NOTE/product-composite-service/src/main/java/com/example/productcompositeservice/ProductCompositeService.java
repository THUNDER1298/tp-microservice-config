package com.example.productcompositeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker; // Importer
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProductCompositeService {

    @Autowired
    private RestTemplate restTemplate;

    // --- Appel au Service Produit ---
    // Si cette méthode échoue, elle appelle "getFallbackProduct"
    @CircuitBreaker(name = "productService", fallbackMethod = "getFallbackProduct")
    public Product getProduct(Long productId) {
        return restTemplate.getForObject(
            "http://PRODUCT-SERVICE/products/{productId}", 
            Product.class, 
            productId
        );
    }

    // --- Appel au Service Review ---
    @CircuitBreaker(name = "reviewService", fallbackMethod = "getFallbackReviews")
    public List<Review> getReviews(Long productId) {
        Review[] reviewsArray = restTemplate.getForObject(
            "http://REVIEW-SERVICE/reviews?productId={productId}", 
            Review[].class, 
            productId
        );
        return (reviewsArray != null) ? Arrays.asList(reviewsArray) : new ArrayList<>();
    }

    // --- Appel au Service Recommendation ---
    @CircuitBreaker(name = "recommendationService", fallbackMethod = "getFallbackRecommendations")
    public List<Recommendation> getRecommendations(Long productId) {
        Recommendation[] recoArray = restTemplate.getForObject(
            "http://RECOMMENDATION-SERVICE/recommendations?productId={productId}", 
            Recommendation[].class, 
            productId
        );
        return (recoArray != null) ? Arrays.asList(recoArray) : new ArrayList<>();
    }

    // --- MÉTHODES FALLBACK (en cas d'échec) ---
    // Le nom et les arguments DOIVENT correspondre
    
    public Product getFallbackProduct(Long productId, Throwable t) {
        // Retourne un produit par défaut si le service est mort
        Product fallbackProduct = new Product();
        fallbackProduct.setProductId(productId);
        fallbackProduct.setName("Produit indisponible (Fallback)");
        fallbackProduct.setWeight(0);
        return fallbackProduct;
    }

    public List<Review> getFallbackReviews(Long productId, Throwable t) {
        // Retourne une liste vide si le service est mort
        return new ArrayList<>();
    }
    
    public List<Recommendation> getFallbackRecommendations(Long productId, Throwable t) {
        // Retourne une liste vide si le service est mort
        return new ArrayList<>();
    }
}