package com.rockchen.springbootshopmall.service.impl;

import com.rockchen.springbootshopmall.dao.repository.UserRepository;
import com.rockchen.springbootshopmall.dto.UserLoginRequest;
import com.rockchen.springbootshopmall.dto.UserRegisterRequestDto;
import com.rockchen.springbootshopmall.model.User;
import com.rockchen.springbootshopmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;
//    UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Integer register(UserRegisterRequestDto userRegisterRequestDto) {
        // 1.檢查 Email是否已經被註冊
        User user = userRepository.findByEmail(userRegisterRequestDto.getEmail());
        // 檢查註冊的 email- 如果email資料已經存在，就不去創建
        if( user != null ){
            log.warn("該電子信箱 {} 已經被註冊", userRegisterRequestDto.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該電子信箱已經被註冊!");
        }

        // 2.密碼加密：使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequestDto.getPassword().getBytes());
        userRegisterRequestDto.setPassword(hashedPassword);

        // 3. 註冊 新User 帳號：前述檢查都完成後才會到這一步
        User newUser = new User();
        newUser.setEmail(userRegisterRequestDto.getEmail());
        newUser.setPassword(userRegisterRequestDto.getPassword());

        Date time = new Date();
        newUser.setCreatedDate(time);
        newUser.setLastModifiedDate(time);

        // 4. 將新User帳號，儲存於資料庫
        newUser = userRepository.save(newUser);

        // 5. 回傳新用戶的Id
        return newUser.getUserId();
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        // 1. 檢查 user 是否存在
        User user = userRepository.findByEmail(userLoginRequest.getEmail());

        if(user == null){
            log.warn("此信箱 {} 還不存在，尚未註冊" ,userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"請註冊帳號");
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
