package com.example.productservice;

// On importe les classes nécessaires
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Gère getters/setters/etc. (si vous avez mis Lombok)
@Entity // Dit à JPA que c'est une table de la base de données
public class Product {

    @Id // Marque ce champ comme la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // L'ID sera auto-généré
    private Long productId;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String name;

    @DecimalMin(value = "0.0", message = "Le poids ne peut être négatif")
    @DecimalMax(value = "100.0", message = "Le poids ne doit pas dépasser 100kg")
    private double weight;
}