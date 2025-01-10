package com.rockchen.springbootshopmall.service;

import com.rockchen.springbootshopmall.dto.ProductQueryParams;
import com.rockchen.springbootshopmall.dto.ProductRequestDto;
import com.rockchen.springbootshopmall.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

//    Optional<Product> getProductById(Integer productId);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequestDto productRequestDto);

    void updateProduct(Integer productId, ProductRequestDto productRequestDto);

    void deleteProductById(Integer productId);


}
