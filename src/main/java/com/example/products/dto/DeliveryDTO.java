package com.example.products.dto;

import com.example.products.models.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DeliveryDTO {
    private int id;
    private PackingList packingList;
    private Product product;
    private int amount;
}
