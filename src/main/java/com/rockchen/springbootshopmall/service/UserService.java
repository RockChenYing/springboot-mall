package com.rockchen.springbootshopmall.service;

import com.rockchen.springbootshopmall.dto.UserRegisterRequest;
import com.rockchen.springbootshopmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);


}
