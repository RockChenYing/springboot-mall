package com.rockchen.springbootshopmall.service.impl;

import com.rockchen.springbootshopmall.dao.UserDao;
import com.rockchen.springbootshopmall.dto.UserRegisterRequest;
import com.rockchen.springbootshopmall.model.User;
import com.rockchen.springbootshopmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
