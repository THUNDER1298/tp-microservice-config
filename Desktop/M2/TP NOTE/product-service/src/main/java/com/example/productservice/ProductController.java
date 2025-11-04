package com.example.productservice;

// ... (gardez tous vos imports actuels)
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

// 1. IMPORTER LE LOGGER ET ENVIRONMENT
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

@RestController
@RequestMapping("/products")
public class ProductController {

    // 2. AJOUTER LE LOGGER ET ENVIRONMENT
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private Environment environment;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // Endpoint pour récupérer un produit par son ID (GET /products/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        
        // 3. AJOUTER LA LIGNE DE LOG
        String port = environment.getProperty("local.server.port");
        logger.info(">>>>>> [TACHE 4] Appel 'Product' reçu sur le port : " + port + " <<<<<<");

        Optional<Product> product = productRepository.findById(id);
        
        return product.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}