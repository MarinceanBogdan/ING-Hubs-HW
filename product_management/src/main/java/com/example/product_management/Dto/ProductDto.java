package com.example.product_management.Dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ProductDto {
    private String name;
    private String category;
    private String description;
    private float price;
    private int quantity;
}
