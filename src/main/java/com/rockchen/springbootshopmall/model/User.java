package com.rockchen.springbootshopmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @JsonProperty("e_mail") // JSON屬性 與 實體變數名稱不同所使用
    @Column(name = "email")
    private String email;

    // 不要顯示密碼Info於JSON中
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP) // 保存完整的日期和時間。
    @Column(name = "created_date", updatable = false) // 不允許修改
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;


}
