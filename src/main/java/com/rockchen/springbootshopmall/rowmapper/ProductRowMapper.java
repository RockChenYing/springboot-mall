package com.rockchen.springbootshopmall.rowmapper;

import com.rockchen.springbootshopmall.constant.ProductCategory;
import com.rockchen.springbootshopmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *   Repository重構完後可以註解掉
 *   因為 Jdbc 的 RowMapper 層是手動將資料庫轉為 Java 對象
 */
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));

        // String 轉成 Enum的過程
        String categoryStr = resultSet.getString("category");
        ProductCategory category = ProductCategory.valueOf(categoryStr);
        product.setCategory(category);

//      寫法2：product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));


        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreatedDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return product;
    }
}
