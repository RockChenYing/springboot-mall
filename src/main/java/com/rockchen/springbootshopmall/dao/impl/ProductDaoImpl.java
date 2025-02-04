//package com.rockchen.springbootshopmall.dao.impl;
//
//import com.rockchen.springbootshopmall.dao.ProductDao;
//import com.rockchen.springbootshopmall.dto.ProductQueryParams;
//import com.rockchen.springbootshopmall.dto.ProductRequestDto;
//import com.rockchen.springbootshopmall.model.Product;
//import com.rockchen.springbootshopmall.rowmapper.ProductRowMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
//@Component
//public class ProductDaoImpl implements ProductDao {
//
//    @Autowired
//    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @Override
//    public Integer countProduct(ProductQueryParams productQueryParams) {
//        String sql = "SELECT count(*) FROM product WHERE 1=1";
//
//        Map<String, Object> map = new HashMap<>();
//
//        sql = addFilteringSql(sql, map, productQueryParams);
//
//        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class); // 將count轉換成Integer
//
//        return total;
//    }
//
//    @Override
//    public List<Product> getProducts(ProductQueryParams productQueryParams) {
//        String sql = "SELECT product_id, product_name, category, image_url, price, stock, " +
//                     "description, created_date,last_modified_date " +
//                     "FROM product WHERE 1=1"; //WHERE 1=1 不會影響查詢的數據，但可以讓sql語法與 AND 拼接使用
//        Map<String,Object> map = new HashMap<>();
//
//        sql = addFilteringSql(sql, map, productQueryParams);
//
//        // 拼接ORDER BY的SQL語法，並依據OrderBy所指定欄位，來執行升序或降序動作
//        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();
//
//        // 3.分頁
//        sql = sql + " LIMIT :limit OFFSET :offset ";
//        map.put("limit", productQueryParams.getLimit());
//        map.put("offset", productQueryParams.getOffset());
//
//        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
//
//        return productList;
//    }
//
//    @Override
//    public Product getProductById(Integer productId) {
//        String sql = "Select product_id, product_name, category, image_url, price, stock, description, " +
//                "created_date, last_modified_date " +
//                "from product where product_id = :productId";
//        Map<String,Object> map = new HashMap<>();
//        map.put("productId", productId);
//
//        List<Product> productList = namedParameterJdbcTemplate.query(sql, map , new ProductRowMapper()); // 接住query結果
//
////        return productList.stream().findFirst();
//
//        if (productList.size()>0){
//            return productList.get(0);
//        }else{
//            return null;
//        }
//    }
//    @Override
//    public Integer createProduct(ProductRequestDto productRequestDto){
//        String sql = "INSERT INTO product(product_name, category, image_url, price, stock," +
//                "description, created_date, last_modified_date)"+
//                "VALUES (:productName, :category,:imageUrl, :price, :stock, :description," +
//                " :createdDate, :lastModifiedDate)";
//        // 此處創建一個map，來將前端送來的請求參數一個一個加到map中
//        Map<String,Object> map = new HashMap<>();
//        map.put("productName", productRequestDto.getProductName());
//        map.put("category", productRequestDto.getCategory().toString());
//        map.put("imageUrl", productRequestDto.getImageUrl());
//        map.put("price", productRequestDto.getPrice());
//        map.put("stock", productRequestDto.getStock());
//        map.put("description", productRequestDto.getDescription());
//
//        // New一個Date出來，用以記錄當下的時間點
//        Date now = new Date();
//        map.put("createdDate", now);
//        map.put("lastModifiedDate", now);
//        // 使用 KeyHolder 儲存db自動生成的productId
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
//        // 由於id是INT，故用intValue()取得Id的值，long則是longValue()
//        int productId = keyHolder.getKey().intValue();
//
//        return productId;
//
//    }
//
//    @Override
//    public void updateProduct(Integer productId, ProductRequestDto productRequestDto) {
//        String sql = "UPDATE product SET product_name =:productName, category = :category, image_url=:imageUrl," +
//                "price =:price, stock=:stock, description=:description, last_modified_date=:lastModifiedDate" +
//                " WHERE product_id = :productId "; // 修改商品時，也要去儲存「商品最後修改的時間」
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("productId",productId);
//
//        map.put("productName", productRequestDto.getProductName());
//        // 由於Esum本身值是物件，資料庫不認識，若不轉成字串格式會導致傳入資料庫錯誤
//        map.put("category", productRequestDto.getCategory().toString());
//        map.put("imageUrl", productRequestDto.getImageUrl());
//        map.put("price", productRequestDto.getPrice());
//        map.put("stock", productRequestDto.getStock());
//        map.put("description", productRequestDto.getDescription());
//        // 它是檢測數據變更的「版本標記」，避免不同用戶的更改互相覆蓋。
//        // new Date 來記錄當下的時間點
//        map.put("lastModifiedDate", new Date());
//
//        namedParameterJdbcTemplate.update(sql,map);
//
//
//    }
//
//    @Override
//    public void updateStock(Integer productId, Integer stock) {
//        String sql = " UPDATE product " +
//                     " SET stock = :stock, last_modified_date = :lastModifiedDate " +
//                     " WHERE product_id = :productId ";
//        Map<String,Object> map = new HashMap<>();
//        map.put("productId",productId);
//        map.put("stock",stock);
//        map.put("lastModifiedDate",new Date());
//
//        namedParameterJdbcTemplate.update(sql,map);
//
//    }
//
//    @Override
//    public void deleteProductById(Integer productId) {
//        String sql = "DELETE FROM product WHERE product_Id = :productId";
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("productId", productId);
//
//        namedParameterJdbcTemplate.update(sql,map);
//    }
//
//    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams){
//        // 1.查詢條件
//        if(productQueryParams.getCategory() != null){
//            sql = sql + " AND category = :category ";
//            map.put("category", productQueryParams.getCategory().name());
//        }
//        // 2.排序
//        if(productQueryParams.getSearch() !=null){
//            sql = sql + " AND product_name LIKE :search";
//            map.put("search","%" + productQueryParams.getSearch() + "%");
//        }
//        return sql;
//    }
//}
