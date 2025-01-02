package com.rockchen.springbootshopmall.dao;
import com.rockchen.springbootshopmall.dto.ProductRequest;
import com.rockchen.springbootshopmall.model.Product;

import java.util.Optional;


public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
