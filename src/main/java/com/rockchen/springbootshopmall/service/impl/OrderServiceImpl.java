package com.rockchen.springbootshopmall.service.impl;

import com.rockchen.springbootshopmall.dao.OrderDao;
import com.rockchen.springbootshopmall.dao.ProductDao;
import com.rockchen.springbootshopmall.dto.BuyItem;
import com.rockchen.springbootshopmall.dto.CreateOrderRequest;
import com.rockchen.springbootshopmall.model.Order;
import com.rockchen.springbootshopmall.model.OrderItem;
import com.rockchen.springbootshopmall.model.Product;
import com.rockchen.springbootshopmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        if(order != null) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

            order.setOrderItemList(orderItemList);
        }else{
            throw new IllegalArgumentException("Order not found for ID: " + orderId);
        }
        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId()); // 取得商品info

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);

        }
        // 創建訂單，由於Order是由兩張table管理，因此呼叫Dao，創建資料在Order，接著在創建於OrderItem
        Integer orderId = orderDao.createOrder(userId,totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
