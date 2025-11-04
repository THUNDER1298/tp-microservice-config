package com.example.reviewservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId; // L'ID unique de cet avis [cite: 20]

    // C'est la "clé étrangère" qui nous dira à quel produit cet avis appartient
    private Long productId; // [cite: 19]

    private String author;  // [cite: 21]
    private String subject; // [cite: 22]
    private String content; }