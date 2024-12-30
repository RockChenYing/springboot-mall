package com.rockchen.springbootshopmall.dao;
import com.rockchen.springbootshopmall.model.Product;

import java.util.Optional;


public interface ProductDao {

    Product getProductById(Integer productId);
}
