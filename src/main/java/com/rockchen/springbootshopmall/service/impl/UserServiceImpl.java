package com.rockchen.springbootshopmall.service.impl;

import com.rockchen.springbootshopmall.dao.UserDao;
import com.rockchen.springbootshopmall.dto.UserLoginRequest;
import com.rockchen.springbootshopmall.dto.UserRegisterRequest;
import com.rockchen.springbootshopmall.model.User;
import com.rockchen.springbootshopmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 我預期在userDao中有個方法getUserByEmail，從前端回傳的訊息中，來確認信箱資料是否存在

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        // 檢查註冊的 email- 如果email資料已經存在，就不會去創建一個新的資料出來
        if( user != null ){
            log.warn("該電子信箱 {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        // 檢查user是否存在


        // 檢查 user 是否存在
        if(user == null){
            log.warn("此信箱 {} 還不存在，尚未註冊" ,userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 密碼檢驗
        if(user.getPassword().equals(hashedPassword)){
            return user;
        }else{
            log.warn("email {} 的密碼錯誤，請重新輸入:",userLoginRequest.getEmail());
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
