package com.rockchen.springbootshopmall.model;

import lombok.Data;

@Data
public class OrderItem {

    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
    // 擴充出來的變數
    private String productName;
    private String imageUrl;
}
