package com.rockchen.springbootshopmall.service;

import com.rockchen.springbootshopmall.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
