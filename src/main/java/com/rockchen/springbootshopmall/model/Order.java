package com.rockchen.springbootshopmall.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`order`")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
    // 擴充新變數
    /*
        @OneToMany JPA的關聯性註解 - 一個訂單可擁有多個商品細項
        mappedBy = "order - 關係是由OrderItem中的order屬性來維護，order必須對應到OrderItem的屬性名稱
        cascade = CascadeType.ALL  -  級聯操作設置ALL，代表Order跟OrderItem是生命共同體，
                                      當Order有任何操作(如刪除、保存)都跟OrderItem連動。
        fetch = FetchType.LAZY  -  指定為懶加載模式，只在實際訪問 orderItemList時才會從資料庫加載數據，提高效能
     */
    @JsonManagedReference
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

}















