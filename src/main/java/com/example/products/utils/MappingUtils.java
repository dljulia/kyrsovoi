package com.example.products.utils;

import com.example.products.dto.DeliveryDTO;
import com.example.products.dto.ProductDTO;
import com.example.products.models.Delivery;
import com.example.products.models.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class MappingUtils {
    @Autowired
    private ModelMapper modelMapper;

    public Product convertToProduct(ProductDTO productDTO) throws ProductException{
        if(productDTO == null){
            throw new ProductException("Get empty object");
        }
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO convertToProductDTO(Product product) throws ProductException{
        if(product == null){
            throw new ProductException("Get empty object");
        }
        return modelMapper.map(product, ProductDTO.class);
    }

    public Delivery convertToDelivery(DeliveryDTO deliveryDTO) throws DeliveryException{
        if(deliveryDTO == null){
            throw new DeliveryException("Get empty object");
        }
        return modelMapper.map(deliveryDTO, Delivery.class);
    }

    public DeliveryDTO convertToDeliveryDTO(Delivery delivery) throws DeliveryException{
        if(delivery == null){
            throw new DeliveryException("Get empty object");
        }
        return modelMapper.map(delivery, DeliveryDTO.class);
    }
}
