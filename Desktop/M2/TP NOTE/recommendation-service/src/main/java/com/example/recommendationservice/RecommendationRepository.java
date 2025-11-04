package com.example.recommendationservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    // Trouve toutes les recommandations pour un productId donné
    List<Recommendation> findByProductId(Long productId);
}