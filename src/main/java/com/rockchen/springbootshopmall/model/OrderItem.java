package com.rockchen.springbootshopmall.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // 外鍵
    private Order order;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;  // 修改為單一 product 屬性
//    private Product productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "amount")
    private Integer amount;
    // -----------------擴充出來的變數-------------------------------


//    @Column(name = "product_name") // 指定資料庫的欄位名稱
//    private String productName;
//
//
//    @Column(name = "image_url") // 指定資料庫的欄位名稱
//    private String imageUrl;
}
