package com.rockchen.springbootshopmall.model;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Date createDate;
    private Date lastModifiedDate;

}
