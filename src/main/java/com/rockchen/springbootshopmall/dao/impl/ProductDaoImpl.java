package com.rockchen.springbootshopmall.dao.impl;

import com.rockchen.springbootshopmall.dao.ProductDao;
import com.rockchen.springbootshopmall.model.Product;
import com.rockchen.springbootshopmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
