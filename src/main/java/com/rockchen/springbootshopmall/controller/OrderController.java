package com.rockchen.springbootshopmall.controller;

import com.rockchen.springbootshopmall.dao.impl.OrderDaoImpl;
import com.rockchen.springbootshopmall.dto.CreateOrderRequest;
import com.rockchen.springbootshopmall.model.Order;
import com.rockchen.springbootshopmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        logger.warn("收到的 userId: {}", userId);
        logger.warn("收到的 CreateOrderRequest: {}", createOrderRequest);

        Integer orderId = orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
