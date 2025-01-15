package com.rockchen.springbootshopmall.service.impl;

import com.rockchen.springbootshopmall.dao.repository.OrderRepository;
import com.rockchen.springbootshopmall.dao.repository.ProductRepository;
import com.rockchen.springbootshopmall.dao.repository.UserRepository;
import com.rockchen.springbootshopmall.dto.BuyItem;
import com.rockchen.springbootshopmall.dto.CreateOrderRequest;
import com.rockchen.springbootshopmall.dto.OrderQueryParams;
import com.rockchen.springbootshopmall.model.Order;
import com.rockchen.springbootshopmall.model.OrderItem;
import com.rockchen.springbootshopmall.model.Product;
import com.rockchen.springbootshopmall.model.User;
import com.rockchen.springbootshopmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override // 計算訂單的總數
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        Integer userId = orderQueryParams.getUserId();
        return orderRepository.countOrder(userId);
    }

    @Override
    public Page<Order> getOrders(Integer userId, Pageable pageable) {
//        List<Order> orderList = orderRepository.findByOrders(userId, pageable);
//
//        for(Order order : orderList) {
//            List<OrderItem> orderItemList = orderRepository.findOrderItemsbyOrderId(order.getOrderId());
//
//            order.setOrderItemList(orderItemList);
//        }
        return orderRepository.findByUserId(userId , pageable);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order != null) {
            // 查詢 OrderItem
            List<OrderItem> orderItemList = orderRepository.findOrderItemsbyOrderId(orderId);
            // 將商品列表填入訂單中
            order.setOrderItemList(orderItemList);

            // 載入每個 OrderItem 產品的照片，但要先確認產品是存在的
            for(OrderItem orderItem : orderItemList){
                if(orderItem.getProduct() != null){
                    String imageUrl = orderItem.getProduct().getImageUrl();
                    logger.info("Product ID: {}, Image URL: {}", orderItem.getProduct().getProductId(), imageUrl);
                }
            }

        }else{
            throw new IllegalArgumentException("Order not found for ID: " + orderId);
        }
        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 1. 檢查使用者 User 是否存在
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"使用者不存在"));

        // 2. 創建訂單
        Order order = new Order();
        order.setUserId(userId);
        Date date = new Date();
        order.setCreatedDate(date);
        order.setLastModifiedDate(date);

        // 3. 處理訂單項目
        List<OrderItem> orderItemList = new ArrayList<>();
        int totalAmount = 0;

        // 檢查 product 是否存在、庫存是否足夠
        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productRepository.findById(buyItem.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"商品不存在")); // 取得商品info
            // 庫存狀況
            if(product.getStock() < buyItem.getQuantity()){
                logger.warn("商品 {} 庫存不足無法購買，剩餘庫存 {} ， 欲購買數量 {}",
                       buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"庫存不足");
            }

            // 更新商品庫存 ： 目前商品庫存 - 購買的商品數量，再存到資料庫
            product.setStock(product.getStock() - buyItem.getQuantity());
            productRepository.save(product);

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // 設置關聯
            orderItem.setProduct(product);
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);

        }

        // 4. 設置訂單總金額和項目列表
        order.setTotalAmount(totalAmount);
        order.setOrderItemList(orderItemList);

        // 5. 儲存訂單
        Order savedOrder = orderRepository.save(order);

        return savedOrder.getOrderId();
    }
}