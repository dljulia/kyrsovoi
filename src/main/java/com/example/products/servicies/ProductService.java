package com.example.products.servicies;

import com.example.products.models.Product;
import com.example.products.repositories.ProductRepo;
import com.example.products.utils.ProductException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.*;

@Service
@NoArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    private final Logger logger = Logger.getLogger(ProductService.class.getName());

    @Transactional
    public Product createProduct(Product product) throws ProductException, IOException {
        Product saveProduct = productRepo.save(product);
        
        setFileHandler();

        if(saveProduct == null){
            logger.log(Level.CONFIG, "Продукт не добавился!");
            throw new ProductException("Продукт не добавился!");
        }

        logger.log(Level.INFO,"Продукт добавился! " + saveProduct);
        return saveProduct;
    }

    private void setFileHandler() throws IOException {
        Handler fileHandler = new FileHandler("/Users/dzianistupik/config/config.rtf", 100000*1024, 10, false);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    public List<Product> getProducts() throws ProductException, IOException {
        List<Product> allProducts = productRepo.findAll();

        setFileHandler();

        if(allProducts == null || allProducts.size() == 0){
            logger.log(Level.WARNING, "Продуктов в таблице нет!");
            throw new ProductException("Продуктов в таблице нет!");
        }

        logger.log(Level.INFO,"Получены продукты в количестве " + allProducts.size());
        return allProducts;
    }

    public Product getProductById(int id) throws ProductException, IOException {
        Optional<Product> product = productRepo.findById(id);

        setFileHandler();

        if(product.isEmpty()){
            logger.log(Level.WARNING, "Продукт c id=" + id + " не найден");
            throw new ProductException("Продукт c id=" + id + " не найден");
        }

        logger.log(Level.INFO, "Продукт c id=" + id + " найден");

        return product.get();
    }

    @Transactional
    public Product updateProduct(Product updatedProduct, int id) throws ProductException, IOException {
        updatedProduct.setId(id);
        Product product = productRepo.save(updatedProduct);

        setFileHandler();

        if(product == null){
            logger.log(Level.CONFIG,"Продукт c id=" + id + "не обновился!");
            throw new ProductException("Продукт c id=" + id + " не обновился!");
        }

        logger.log(Level.INFO,"Продукт c id=" + id + " обновился!");
        return product;
    }

    @Transactional
    public Product deleteProduct(int id) throws ProductException, IOException {
        Product deletedProduct = getProductById(id);
        productRepo.delete(deletedProduct);

        setFileHandler();

        if(deletedProduct == null){
            logger.log(Level.CONFIG,"Продукт c id=" + id + "не удалился!");
            throw new ProductException("Продукт c id=" + id + "не удалился!");
        }

        logger.log(Level.INFO,"Продукт id=" + id + " удалился!");
        return deletedProduct;
    }
}
