package com.rockchen.springbootshopmall.service.impl;

import com.rockchen.springbootshopmall.dao.OrderDao;
import com.rockchen.springbootshopmall.dao.ProductDao;
import com.rockchen.springbootshopmall.dao.UserDao;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for(Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

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
        User user = userDao.getUserById(userId);
        if(user == null){
            logger.warn(" 該使用者Id {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId()); // 取得商品info

            // 檢查 product 是否存在、庫存是否足夠
            if(product == null){
                logger.warn("商品 {} 不存在 ", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else{
                if(product.getStock() < buyItem.getQuantity()){
                    logger.warn("商品 {} 庫存不足無法購買，剩餘庫存 {} ，欲購買數量 {} ",
                                product.getStock(), buyItem.getQuantity());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }

            // 扣除商品庫存- 目前商品庫存 減去 購買的商品數量
            productDao.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());

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
