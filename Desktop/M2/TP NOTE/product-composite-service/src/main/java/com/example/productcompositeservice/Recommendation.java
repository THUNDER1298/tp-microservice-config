package com.example.productcompositeservice;

import lombok.Data;

@Data
public class Recommendation {
    // Ces champs doivent correspondre au JSON envoyé par recommendation-service
    private Long recommendationId;
    private Long productId;
    private String author;
    private int rate;
    private String content;
}
