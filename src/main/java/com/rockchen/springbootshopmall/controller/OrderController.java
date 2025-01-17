package com.rockchen.springbootshopmall.controller;

import com.rockchen.springbootshopmall.dto.CreateOrderRequest;
import com.rockchen.springbootshopmall.dto.OrderResponseDto;
import com.rockchen.springbootshopmall.model.Order;
import com.rockchen.springbootshopmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Validated
@RestController
public class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page, // 第幾頁，從第 0 頁開始
            @RequestParam(defaultValue = "10") int size // 每頁的筆數
//            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
//            @RequestParam(defaultValue = "0") @Min(0) Integer offSet
    ){
        // 創建 Pageable：依照創建的時間來排序，並採用降序方式，從新到舊
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // 實作 orderService層 的方法，並返回結果
        Page<Order> orders = orderService.getOrders(userId,pageable);
        return ResponseEntity.ok(orders);


//        OrderQueryParams orderQueryParams = new OrderQueryParams();
//        orderQueryParams.setUserId(userId);
//        orderQueryParams.setLimit(limit);
//        orderQueryParams.setOffSet(offSet);


//        // 取得 order List
//        List<Order> orderList = orderService.getOrders(orderQueryParams);
//
//        // 取得 order 總數
//        Integer count = orderService.countOrder(orderQueryParams);
//
//        // 分頁
//        Page<Order> page = new Page<>();
//        page.setLimit(limit);
//        page.setOffset(offSet);
//        page.setTotal(count);
//        page.setResults(orderList);
//
//        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createdOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        logger.warn("收到的 userId: {}", userId);
        logger.warn("收到的 CreateOrderRequest: {}", createOrderRequest);

        Integer orderId = orderService.createOrder(userId,createOrderRequest);
        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponseDto.fromOrder(order));
        //直接返回order會陷入無限遞迴
//        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}
