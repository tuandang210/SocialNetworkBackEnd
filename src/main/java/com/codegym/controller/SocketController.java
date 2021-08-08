package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("*")
@RestController
public class SocketController {
    @Autowired
    private IProductService productService;

    @MessageMapping("/products")
    @SendTo("/topic/products")
    public Product createNewProductUsiSocket(Product product) {
        return productService.save(product);
    }
}
