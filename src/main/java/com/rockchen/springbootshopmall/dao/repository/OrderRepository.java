package com.rockchen.springbootshopmall.dao.repository;

import com.rockchen.springbootshopmall.model.Order;

import com.rockchen.springbootshopmall.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Pageable為 JPA 所提供，用來分頁排序的功能，會自動轉換為 SQL 的 LIMIT 和 OFFSET
    Page<Order> findByUserId(Integer userId, Pageable pageable);

    @Query(" SELECT COUNT(o) FROM Order o WHERE " +
            "(:userId IS NULL OR o.userId = :userId)")
    Integer countOrder(@Param("userId") Integer userId);

    @Query(" SELECT oi FROM OrderItem oi WHERE " +
           "(:orderId IS NULL OR oi.order.orderId = :orderId)")
    List<OrderItem> findOrderItemsbyOrderId(@Param("orderId") Integer orderId);
}
