package com.rockchen.springbootshopmall.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// 前端所回傳過來使用者購買的 JSON資料內部
@Data
public class BuyItem {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;
}
