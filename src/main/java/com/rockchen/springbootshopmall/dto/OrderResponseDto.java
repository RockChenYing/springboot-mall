package com.rockchen.springbootshopmall.dto;

import com.rockchen.springbootshopmall.model.Order;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
    解決JSON遞迴問題的DTO
 */

@Data
public class OrderResponseDto {
    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Date createdDate;
    private Date lastModifiedDate;
    private List<OrderItemResponse> orderItemList;

    @Data
    public static class OrderItemResponse {
        private Integer orderItemId;
        private Integer productId;
        private Integer quantity;
        private Integer amount;
    }

    // 轉換方法
    public static OrderResponseDto fromOrder(Order order) {
        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(order.getOrderId());
        response.setUserId(order.getUserId());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedDate(order.getCreatedDate());
        response.setLastModifiedDate(order.getLastModifiedDate());

        response.setOrderItemList(order.getOrderItemList().stream()
                .map(item -> {
                    OrderItemResponse itemResponse = new OrderItemResponse();
                    itemResponse.setOrderItemId(item.getOrderItemId());
                    itemResponse.setProductId(item.getProduct().getProductId());
                    itemResponse.setQuantity(item.getQuantity());
                    itemResponse.setAmount(item.getAmount());
                    return itemResponse;
                })
                .collect(Collectors.toList()));

        return response;
    }
}