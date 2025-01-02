package com.rockchen.springbootshopmall.dao;
import com.rockchen.springbootshopmall.constant.ProductCategory;
import com.rockchen.springbootshopmall.dto.ProductRequest;
import com.rockchen.springbootshopmall.model.Product;

import java.util.List;
import java.util.Optional;


public interface ProductDao {

    List<Product> getProducts(ProductCategory category,String search);

    Product getProductById(Integer productId);

//    Optional<Product> getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
