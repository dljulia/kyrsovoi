package com.example.products.servicies;

import com.example.products.models.Delivery;
import com.example.products.models.Product;
import com.example.products.repositories.DeliveryRepo;
import com.example.products.utils.DeliveryException;
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
public class DeliveryService {
    @Autowired
    private DeliveryRepo deliveryRepo;
    @Autowired
    private ProductService productService;
    private final Logger logger = Logger.getLogger(DeliveryService.class.getName());

    @Transactional
    public Delivery createDelivery(Delivery delivery) throws DeliveryException, IOException, ProductException {
        setFileHandler();

        Product product = productService.getProductById(delivery.getProduct().getId());

        if (product.getAmount() < delivery.getAmount()){
            int diff = delivery.getAmount() - product.getAmount();
            logger.log(Level.SEVERE, "Заказ не добавился! Количество продуктов в заказе больше исходного на " + diff);
            throw new DeliveryException("Заказ не добавился! Количество продуктов в заказе больше исходного на " + diff);
        }

        Delivery saveDelivery = deliveryRepo.save(delivery);

        if(saveDelivery == null){
            logger.log(Level.CONFIG, "Заказ не добавился!");
            throw new DeliveryException("Заказ не добавился!");
        }

        logger.log(Level.INFO, "Заказ добавился! " + saveDelivery);

        return saveDelivery;
    }

    private void setFileHandler() throws IOException {
        Handler fileHandler = new FileHandler("/Users/dzianistupik/config/config.rtf", 100000*1024, 10, false);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    public List<Delivery> getDelivers() throws DeliveryException, IOException {
        List<Delivery> allDelivers = deliveryRepo.findAll();

        setFileHandler();

        if(allDelivers == null || allDelivers.size() == 0){
            logger.log(Level.WARNING, "Нет заказов");
            throw new DeliveryException("Нет заказов");
        }

        logger.log(Level.INFO, "Заказы найдены в количестве " + allDelivers.size());

        return allDelivers;
    }

    public Delivery getDeliveryById(int id) throws DeliveryException, IOException {
        Optional<Delivery> delivery = deliveryRepo.findById(id);

        setFileHandler();

        if(delivery.isEmpty()){
            logger.log(Level.WARNING, "Заказ c id=" + id + " не найден");
            throw new DeliveryException("Заказ c id=" + id + " не найден");
        }

        logger.log(Level.INFO, "Заказ c id=" + id + "найден");

        return delivery.get();
    }

    @Transactional
    public Delivery updateDelivery(Delivery updatedDelivery, int id) throws DeliveryException, IOException, ProductException {
        updatedDelivery.setId(id);
        setFileHandler();

        Product product = productService.getProductById(updatedDelivery.getProduct().getId());

        if(product.getAmount() < updatedDelivery.getAmount()){
            int diff = updatedDelivery.getAmount() - product.getAmount();
            logger.log(Level.SEVERE, "Заказ c id=" + id + " не обновился! Количество продуктов в заказе больше исходного на " + diff);
            throw new DeliveryException("Заказ c id=" + id + " не обновился! Количество продуктов в заказе больше исходного на " + diff);
        }

        Delivery delivery = deliveryRepo.save(updatedDelivery);

        if(delivery == null){
            logger.log(Level.SEVERE, "Заказ не добавился!");
            throw new DeliveryException("Заказ не добавился!");
        }

        logger.log(Level.INFO, "Заказ c id=" + id + "обновился");

        return delivery;
    }

    @Transactional
    public Delivery deleteDelivery(int id) throws DeliveryException, IOException {
        Delivery deletedDelivery = getDeliveryById(id);
        deliveryRepo.delete(deletedDelivery);

        setFileHandler();

        if(deletedDelivery == null){
            logger.log(Level.CONFIG, "Заказ c id=" + id + "не удалился");
            throw new DeliveryException("Заказ c id=" + id + "не удалился");
        }
        
        logger.log(Level.INFO, "Заказ c id=" + id + " удалился");

        return deletedDelivery;
    }
}
