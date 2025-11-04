package com.example.recommendationservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max; // Importation pour la validation
import jakarta.validation.constraints.Min; // Importation pour la validation
import lombok.Data;

@Data
@Entity
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId; // ID unique

    private Long productId; // Produit lié
    private String author;
    private String content;

    // Validation du TP : Le champ Rate est un pourcentage (0% à 100%)
    @Min(value = 0, message = "Rate doit être au minimum 0")
    @Max(value = 100, message = "Rate doit être au maximum 100")
    private int rate; // On utilise 'int' pour un pourcentage simple
}