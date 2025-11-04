package com.example.productservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // C'est tout !
    // Spring Data JPA va créer les méthodes findById, save, findAll...
    // automatiquement.
}