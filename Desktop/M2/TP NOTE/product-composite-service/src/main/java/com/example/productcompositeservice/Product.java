package com.example.productcompositeservice;

import lombok.Data;

@Data // Gère les getters/setters
public class Product {
    // Ces champs doivent correspondre au JSON envoyé par product-service
    private Long productId;
    private String name;
    private double weight;
}