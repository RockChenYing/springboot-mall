package com.rockchen.springbootshopmall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 接受前端所送過來的Date
@Data
public class UserRegisterRequestDto {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
