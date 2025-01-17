package com.rockchen.springbootshopmall.controller;

import com.rockchen.springbootshopmall.dto.UserLoginRequest;
import com.rockchen.springbootshopmall.dto.UserRegisterRequestDto;
import com.rockchen.springbootshopmall.model.User;
import com.rockchen.springbootshopmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        Integer userId = userService.register(userRegisterRequestDto);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user); //body是要回應客戶端用的
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
//    // 驗證會員來登入產品界面
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
//        User user = userService.getUserById(userId);
//        if (user != null) {
//            return ResponseEntity.status(HttpStatus.OK).body(user);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }


}
