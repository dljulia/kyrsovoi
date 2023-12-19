package com.example.products.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private int amount;
}
