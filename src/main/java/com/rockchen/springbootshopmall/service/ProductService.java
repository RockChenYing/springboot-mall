package com.rockchen.springbootshopmall.service;

import com.rockchen.springbootshopmall.constant.ProductCategory;
import com.rockchen.springbootshopmall.dao.ProductQueryParams;
import com.rockchen.springbootshopmall.dto.ProductRequest;
import com.rockchen.springbootshopmall.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

//    Optional<Product> getProductById(Integer productId);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);


}
