package com.rockchen.springbootshopmall.controller;

import com.rockchen.springbootshopmall.model.Product;
import com.rockchen.springbootshopmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products/{productId}")
    // ResponseEntity<> 自定義回傳的狀態碼 http response 的細節
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
