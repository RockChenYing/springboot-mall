package com.rockchen.springbootshopmall.dao.repository;

import com.rockchen.springbootshopmall.constant.ProductCategory;
import com.rockchen.springbootshopmall.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 已經自動將JpaRepository接口作為Bean註冊
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // 自定義查詢，支持分頁及篩選功能
    @Query(" SELECT p FROM Product p WHERE " +
            "(:category IS NULL OR p.category = :category) AND " +  // IS NULL 是過濾條件
            "(:search IS NULL OR p.productName LIKE %:search%)")
    List<Product> findProductByQuery(
            @Param("category") ProductCategory category, // Enum類型
            @Param("search") String search );

    @Query(" SELECT COUNT(p) FROM Product p WHERE " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:search IS NULL OR p.productName LIKE %:search%)")
    Integer countProductsByQuery(
            @Param("category") ProductCategory category,
            @Param("search") String search);

}
