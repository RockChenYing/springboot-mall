package com.rockchen.springbootshopmall.dao;

import com.rockchen.springbootshopmall.constant.ProductCategory;
import lombok.Data;

/**
 * 集中將回傳變數，放在這裡，這樣就不需要到各個層中去修改回傳的參數
 *
 */

@Data
public class ProductQueryParams {

    private ProductCategory category;
    private String search;
    private String orderBy;
    private String sort;
}
