//package com.rockchen.springbootshopmall.dao;
//
//import com.rockchen.springbootshopmall.dto.OrderQueryParams;
//import com.rockchen.springbootshopmall.model.Order;
//import com.rockchen.springbootshopmall.model.OrderItem;
//
//import java.util.List;
//
//public interface OrderDao {
//
//    Integer countOrder(OrderQueryParams orderQueryParams);
//
//    List<Order> getOrders(OrderQueryParams orderQueryParams);
//
//    Order getOrderById(Integer orderId);
//
//    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
//
//    Integer createOrder(Integer userId, Integer totalAmount);
//
//    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
//}
