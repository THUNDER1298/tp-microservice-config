package com.example.productcompositeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Ajouté
import org.springframework.web.bind.annotation.PutMapping; // Ajouté
import org.springframework.web.bind.annotation.RequestBody; // Ajouté
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment; 

// 1. IMPORTER MICROMETER
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class ProductCompositeController {

    private static final Logger logger = LoggerFactory.getLogger(ProductCompositeController.class);

    @Autowired
    private ProductCompositeService compositeService; 
    
    @Autowired
    private Environment environment; 

    // 2. DÉCLARER LES COMPTEURS
    private final Counter getCounter;
    private final Counter postPutCounter;

    // 3. INITIALISER LES COMPTEURS DANS LE CONSTRUCTEUR
    public ProductCompositeController(MeterRegistry registry) {
        // Tâche 7.b : Métrique pour les GET [cite: 82-84]
        this.getCounter = Counter.builder("custom.requests.get")
            .description("Nombre de requêtes GET reçues par le composite")
            .register(registry);
            
        // Tâche 7.a : Métrique pour les POST/PUT [cite: 80-81]
        this.postPutCounter = Counter.builder("custom.requests.post_put")
            .description("Nombre de requêtes POST ou PUT reçues par le composite")
            .register(registry);
    }

    // --- NOTRE MÉTHODE GET EXISTANTE ---
    @GetMapping("/product-composite/{productId}")
    public ResponseEntity<Map<String, Object>> getProductComposite(@PathVariable Long productId) {
        
        // 4. INCRÉMENTER LE COMPTEUR GET
        this.getCounter.increment();
        
        String port = environment.getProperty("local.server.port");
        logger.info(">>>>>> Appel GET reçu sur le port : " + port + " <<<<<<");

        Map<String, Object> compositeResponse = new HashMap<>();
        compositeResponse.put("product", compositeService.getProduct(productId));
        compositeResponse.put("reviews", compositeService.getReviews(productId));
        compositeResponse.put("recommendations", compositeService.getRecommendations(productId));
        
        return ResponseEntity.ok(compositeResponse);
    }

    // --- AJOUT DE MÉTHODES POST/PUT (pour tester la Tâche 7.a) ---

    @PostMapping("/product-composite")
    public ResponseEntity<String> createComposite(@RequestBody(required = false) Map<String, Object> body) {
        // 5. INCRÉMENTER LE COMPTEUR POST/PUT
        this.postPutCounter.increment();
        logger.info(">>>>>> Appel POST reçu <<<<<<");
        return ResponseEntity.ok("POST Reçu");
    }

    @PutMapping("/product-composite/{productId}")
    public ResponseEntity<String> updateComposite(@PathVariable Long productId, @RequestBody(required = false) Map<String, Object> body) {
        // 6. INCRÉMENTER LE COMPTEUR POST/PUT
        this.postPutCounter.increment();
        logger.info(">>>>>> Appel PUT reçu <<<<<<");
        return ResponseEntity.ok("PUT Reçu");
    }
}