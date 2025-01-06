package com.rockchen.springbootshopmall.dao;

import com.rockchen.springbootshopmall.dto.UserRegisterRequest;
import com.rockchen.springbootshopmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
