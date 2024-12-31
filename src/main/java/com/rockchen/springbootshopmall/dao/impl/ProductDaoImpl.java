package com.rockchen.springbootshopmall.dao.impl;

import com.rockchen.springbootshopmall.dao.ProductDao;
import com.rockchen.springbootshopmall.dto.ProductRequest;
import com.rockchen.springbootshopmall.model.Product;
import com.rockchen.springbootshopmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "Select product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "from product where product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map , new ProductRowMapper()); // 接住query結果


        if (productList.size()>0){
            return productList.get(0);
        }else{
            return null;
        }
    }
    @Override
    public Integer createProduct(ProductRequest productRequest){
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock," +
                "description, created_date, last_modified_date)"+
                "VALUES (:productName, :category,:imageUrl, :price, :stock, :description," +
                " :createdDate, :lastModifiedDate)";
        // 此處創建一個map，來將前端送來的請求參數一個一個加到map中
        Map<String,Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        // New一個Date出來，用以記錄當下的時間點
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        // 使用 KeyHolder 儲存db自動生成的productId
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        // 由於id是INT，故用intValue()取得Id的值，long則是longValue()
        int productId = keyHolder.getKey().intValue();

        return productId;

    }

}
