package com.rockchen.springbootshopmall.service;

import com.rockchen.springbootshopmall.dto.UserLoginRequest;
import com.rockchen.springbootshopmall.dto.UserRegisterRequestDto;
import com.rockchen.springbootshopmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequestDto userRegisterRequestDto);

    User login(UserLoginRequest userLoginRequest);


}
