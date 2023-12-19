package com.example.products.controllers;

import com.example.products.dto.ProductDTO;
import com.example.products.models.Product;
import com.example.products.servicies.ProductService;
import com.example.products.utils.MappingUtils;
import com.example.products.utils.ProductException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private MappingUtils mappingUtils;
    private final Logger logger = Logger.getLogger(ProductController.class.getName());

    @PostMapping("")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) throws IOException {
        Product product = new Product();
        ProductDTO newProductDTO = new ProductDTO();

        setFileHandler();

        logger.log(Level.INFO, "Попытка добавить продукт");

        try{
            product = productService.createProduct(mappingUtils.convertToProduct(productDTO));
            newProductDTO = mappingUtils.convertToProductDTO(product);
        } catch(ProductException e){
            System.out.println(e.getMessage());
        }

        if(!newProductDTO.equals(productDTO)){
            return null;
        }

        return newProductDTO;
    }

    private void setFileHandler() throws IOException {
        Handler fileHandler = new FileHandler("/Users/dzianistupik/config/config.rtf", 100000*1024, 10, false);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    @GetMapping("")
    public List<ProductDTO> getProducts() throws IOException {
        List<Product> allProducts = new ArrayList<>();
        List<ProductDTO> allProductsDTO = new ArrayList<>();

        setFileHandler();

        logger.log(Level.INFO, "Попытка получить все продукты");

        try {
            allProducts = productService.getProducts();
            for (Product product : allProducts) {
                allProductsDTO.add(mappingUtils.convertToProductDTO(product));
            }
        } catch(ProductException e){
            System.out.println(e.getMessage());
        }

        return allProductsDTO;
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable int id) throws IOException {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        setFileHandler();

        logger.log(Level.INFO, "Попытка получить один продукт");

        try {
            product = productService.getProductById(id);
            productDTO = mappingUtils.convertToProductDTO(product);
        } catch (ProductException e){
            System.out.println(e.getMessage());
        }

        return productDTO;
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@RequestBody ProductDTO updatedProductDTO, @PathVariable int id) throws IOException {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        setFileHandler();

        logger.log(Level.INFO, "Попытка обновить продукт");

        try {
            product = productService.updateProduct(mappingUtils.convertToProduct(updatedProductDTO), id);
            productDTO = mappingUtils.convertToProductDTO(product);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }

        return productDTO;
    }

    @DeleteMapping("/{id}")
    public ProductDTO deleteProduct(@PathVariable int id) throws IOException {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        setFileHandler();

        logger.log(Level.INFO, "Попытка удалить продукт");

        try {
            product = productService.deleteProduct(id);
            productDTO = mappingUtils.convertToProductDTO(product);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }

        return productDTO;
    }
}
