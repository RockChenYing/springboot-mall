package com.rockchen.springbootshopmall.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rockchen.springbootshopmall.constant.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "orderItemList"})
@Entity // JPA 註解，標記這個類是一個實體類。
@Table(name = "product") // 對應到哪張table
@Data
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) // 指定主鍵自動生成
   @Column(name = "product_id")
   private Integer productId;


   @Column(name = "product_name", nullable = false) // 表示該欄位不可為空值
   private String productName;

   @Enumerated(EnumType.STRING)
   @Column(name = "category")
   private ProductCategory category;


   @Column(name = "image_url")
   private String imageUrl;

   @Column(name = "price")
   private Integer price;

   @Column(name = "stock")
   private Integer stock;

   @Column(name = "description")
   private String description;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "created_date", updatable = false) // 創建時間不可被更新
   private Date createdDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "last_modified_date")
   private Date lastModifiedDate;

   @JsonManagedReference
//   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private List<OrderItem> orderItemList;
}
