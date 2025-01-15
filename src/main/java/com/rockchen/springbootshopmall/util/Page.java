package com.rockchen.springbootshopmall.util;

import lombok.Data;

import java.util.List;

// (jdbc時) 分頁功能都在Page的Dto中，是封裝與分頁相關的數據
@Data
public class Page<T> {

    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;
}
