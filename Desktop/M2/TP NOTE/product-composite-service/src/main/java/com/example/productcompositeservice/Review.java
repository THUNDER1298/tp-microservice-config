package com.example.productcompositeservice;

import lombok.Data;

@Data
public class Review {
    // Ces champs doivent correspondre au JSON envoyé par review-service
    private Long reviewId;
    private Long productId;
    private String author;
    private String subject;
    private String content;
}
