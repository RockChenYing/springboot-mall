package com.rockchen.springbootshopmall.service.impl;

import com.rockchen.springbootshopmall.dao.repository.ProductRepository;
import com.rockchen.springbootshopmall.dto.ProductQueryParams;
import com.rockchen.springbootshopmall.dto.ProductRequestDto;
import com.rockchen.springbootshopmall.model.Product;
import com.rockchen.springbootshopmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {


//    @Autowired
//    private ProductDao productDao;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        return productRepository.countProductsByQuery(
                productQueryParams.getCategory(),
                productQueryParams.getSearch()
        );
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productRepository.findProductByQuery(
                productQueryParams.getCategory(),
                productQueryParams.getSearch()
        );
    }

    @Override
    public Product getProductById(Integer productId) {
        // findById 本身是Optional功能
        return productRepository.findById(productId).orElse(null);
    }

//    @Override
//    public Optional<Product> getProductById(Integer productId) {
//        return productDao.getProductById(productId);
//    }

    @Override
    public Integer createProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setProductName(productRequestDto.getProductName());
        product.setCategory(productRequestDto.getCategory());
        product.setImageUrl(productRequestDto.getImageUrl());
        product.setPrice(productRequestDto.getPrice());
        product.setStock(productRequestDto.getStock());
        product.setDescription(productRequestDto.getDescription());

        Date time = new Date();
        product.setCreatedDate(time);
        product.setLastModifiedDate(time);
        // 將上述product所設定的資料，儲存再資料庫中
        Product savedProduct = productRepository.save(product);

        return savedProduct.getProductId();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequestDto productRequestDto) {
        /*
            () -> Java 8 引入的語法糖，用來表示匿名函數或簡化的接口實現。
            () 表示這個 Lambda 沒有參數（即 get() 方法不需要傳入任何參數）。
         */
        // 先檢查商品是否存在
        Product product = productRepository.findById(productId)
                         .orElseThrow(() -> new RuntimeException("商品不存在"));

        product.setProductName(productRequestDto.getProductName());
        product.setCategory(productRequestDto.getCategory());
        product.setImageUrl(productRequestDto.getImageUrl());
        product.setPrice(productRequestDto.getPrice());
        product.setStock(productRequestDto.getStock());
        product.setDescription(productRequestDto.getDescription());
        product.setLastModifiedDate(new Date());
        // 不需回傳結果，只要更新table
        productRepository.save(product);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }

}
