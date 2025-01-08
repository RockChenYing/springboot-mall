package com.rockchen.springbootshopmall.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {

    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Date createDate;
    private Date lastModifiedDate;
    // 擴充新變數
    private List<OrderItem> orderItemList;

}
