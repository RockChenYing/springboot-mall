package com.rockchen.springbootshopmall.controller;

import com.rockchen.springbootshopmall.constant.ProductCategory;
import com.rockchen.springbootshopmall.dao.ProductQueryParams;
import com.rockchen.springbootshopmall.dto.ProductRequest;
import com.rockchen.springbootshopmall.model.Product;
import com.rockchen.springbootshopmall.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category, // required = false => 設定category請求參數不是必填
            @RequestParam(required = false) String search,
            // 排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy, // 根據"create_date"進行欄位排序
            @RequestParam(defaultValue = "desc") String sort,     // 用以表示要升序還降序(desc)來排序

            // 分頁功能 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,   // 要取得幾筆數據
            @RequestParam(defaultValue = "0") @Min(0) Integer offset   // 要跳過多少筆數據
    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        List<Product> productList = productService.getProducts(productQueryParams);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

//    轉成Optional的參考
//    @GetMapping("/products/{productId}")
//    // ResponseEntity<> 自定義回傳的狀態碼 http response 的細節
//    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
//
//        return productService.getProductById(productId)
//                .map(product -> ResponseEntity.status(HttpStatus.ACCEPTED).body(product))
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }

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

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        // 我預期在productService有新增商品的方法，是透過Id的方式
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);
//        productService.getProductById(productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    // 修改步驟有兩個
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){
        // 第一步：先檢查 product 是否存在
        Product product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // 第二步：如果商品有存在，修改商品的數據
        // 使用Service層中，修改商品的方法，透過productId找到商品，再根據dto-productRequest的資料進行更新
        productService.updateProduct(productId, productRequest);
        // 透過 getProductById 方法取得指定 productId 的商品資料，用於"確認"剛剛更新的內容是否正確。
        Product updateProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        // 只要確認欲刪除的商品消失不見，就表示這個刪除是成功的，對前端來說你只要刪除就好了
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 返回204，商品已不存在
    }


}
