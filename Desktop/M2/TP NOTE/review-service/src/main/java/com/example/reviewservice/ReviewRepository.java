package com.example.reviewservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Spring Data JPA va "comprendre" ce nom de méthode.
     * Il va automatiquement créer une requête SQL qui cherche
     * tous les avis (Review) où le champ "productId"
     * correspond au paramètre que nous donnons.
     */
    List<Review> findByProductId(Long productId);
}