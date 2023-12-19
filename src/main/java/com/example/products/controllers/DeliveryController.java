package com.example.products.controllers;

import com.example.products.dto.DeliveryDTO;
import com.example.products.models.Delivery;
import com.example.products.utils.MappingUtils;
import com.example.products.utils.DeliveryException;
import com.example.products.servicies.DeliveryService;
import com.example.products.utils.ProductException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

@RestController
@RequestMapping("/api/deliveries")
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private MappingUtils mappingUtils;
    private final Logger logger = Logger.getLogger(ProductController.class.getName());

    @PostMapping("")
    public DeliveryDTO createDelivery(@RequestBody DeliveryDTO deliveryDTO) throws IOException {
        Delivery delivery = new Delivery();
        DeliveryDTO newDeliveryDTO = new DeliveryDTO();

        setFileHandler();

        logger.log(Level.INFO, "Попытка добавить заказ");

        try{
            delivery = deliveryService.createDelivery(mappingUtils.convertToDelivery(deliveryDTO));
            newDeliveryDTO = mappingUtils.convertToDeliveryDTO(delivery);
        } catch(DeliveryException | ProductException e){
            System.out.println(e.getMessage());
        }

        if(!newDeliveryDTO.equals(deliveryDTO)){
            return null;
        }

        return newDeliveryDTO;
    }

    private void setFileHandler() throws IOException {
        Handler fileHandler = new FileHandler("/Users/dzianistupik/config/config.rtf", 100000*1024, 10, false);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    @GetMapping("")
    public List<DeliveryDTO> getDeliverys() throws IOException {
        List<Delivery> allDeliverys = new ArrayList<>();
        List<DeliveryDTO> allDeliverysDTO = new ArrayList<>();

        setFileHandler();

        logger.log(Level.INFO, "Попытка получить все заказы");

        try {
            allDeliverys = deliveryService.getDelivers();
            for (Delivery delivery : allDeliverys) {
                allDeliverysDTO.add(mappingUtils.convertToDeliveryDTO(delivery));
            }
        } catch(DeliveryException e){
            System.out.println(e.getMessage());
        }

        return allDeliverysDTO;
    }

    @GetMapping("/{id}")
    public DeliveryDTO getDeliveryById(@PathVariable int id) throws IOException {
        Delivery delivery = new Delivery();
        DeliveryDTO deliveryDTO = new DeliveryDTO();

        setFileHandler();

        logger.log(Level.INFO, "Попытка получить один заказ");

        try {
            delivery = deliveryService.getDeliveryById(id);
            deliveryDTO = mappingUtils.convertToDeliveryDTO(delivery);
        } catch (DeliveryException e){
            System.out.println(e.getMessage());
        }

        return deliveryDTO;
    }

    @PutMapping("/{id}")
    public DeliveryDTO updateDelivery(@RequestBody DeliveryDTO updatedDeliveryDTO, @PathVariable int id) throws IOException {
        Delivery delivery = new Delivery();
        DeliveryDTO deliveryDTO = new DeliveryDTO();

        setFileHandler();

        logger.log(Level.INFO, "Попытка обновить заказ");

        try {
            delivery = deliveryService.updateDelivery(mappingUtils.convertToDelivery(updatedDeliveryDTO), id);
            deliveryDTO = mappingUtils.convertToDeliveryDTO(delivery);
        } catch (DeliveryException | ProductException e) {
            System.out.println(e.getMessage());
        }

        return deliveryDTO;
    }

    @DeleteMapping("/{id}")
    public DeliveryDTO deleteDelivery(@PathVariable int id) throws IOException {
        Delivery delivery = new Delivery();
        DeliveryDTO deliveryDTO = new DeliveryDTO();

        setFileHandler();

        logger.log(Level.INFO, "Попытка удалить заказ");

        try {
            delivery = deliveryService.deleteDelivery(id);
            deliveryDTO = mappingUtils.convertToDeliveryDTO(delivery);
        } catch (DeliveryException e) {
            System.out.println(e.getMessage());
        }

        return deliveryDTO;
    }
}

