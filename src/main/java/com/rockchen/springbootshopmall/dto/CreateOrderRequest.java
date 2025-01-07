package com.rockchen.springbootshopmall.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    // 前端所回傳過來使用者購買的 JSON資料
    @NotEmpty // 這個集合不可以是空的，要有內容
    private List<BuyItem> buyItemList;
}
