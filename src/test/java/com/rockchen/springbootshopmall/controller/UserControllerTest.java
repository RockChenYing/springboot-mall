package com.rockchen.springbootshopmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockchen.springbootshopmall.dto.UserLoginRequest;
import com.rockchen.springbootshopmall.dto.UserRegisterRequestDto;
import com.rockchen.springbootshopmall.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper = new ObjectMapper();

    // 註冊新帳號
    @Test
    public void register_success() throws Exception {
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setEmail("test1@gmail.com");
        userRegisterRequestDto.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequestDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo("test1@gmail.com")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));

        // 檢查資料庫中的密碼不為明碼
        User user = userDao.getUserByEmail(userRegisterRequestDto.getEmail());
        assertNotEquals(userRegisterRequestDto.getPassword(), user.getPassword());
    }

    @Test  // 驗證email格式是否正確
    public void register_invalidEmailFormat() throws Exception {
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setEmail("3gd8e7q34l9");
        userRegisterRequestDto.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequestDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void register_emailAlreadyExist() throws Exception {
        // 先註冊一個帳號
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setEmail("test2@gmail.com");
        userRegisterRequestDto.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequestDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));

        // 再次使用同個 email 註冊
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    // 登入
    @Test
    public void login_success() throws Exception {
        // 先註冊新帳號
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setEmail("rock@gmail.com");
        userRegisterRequestDto.setPassword("0000");

        register(userRegisterRequestDto);

        // 再測試登入功能
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(userRegisterRequestDto.getEmail());
        userLoginRequest.setPassword(userRegisterRequestDto.getPassword());

        String json = objectMapper.writeValueAsString(userRegisterRequestDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo(userRegisterRequestDto.getEmail())))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Test
    public void login_wrongPassword() throws Exception {
        // 先註冊新帳號
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setEmail("test4@gmail.com");
        userRegisterRequestDto.setPassword("123");

        register(userRegisterRequestDto);

        // 測試密碼輸入錯誤的情況
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(userRegisterRequestDto.getEmail());
        userLoginRequest.setPassword("unknown");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_invalidEmailFormat() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("hkbudsr324");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_emailNotExist() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("unknown@gmail.com");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    private void register(UserRegisterRequestDto userRegisterRequestDto) throws Exception {
        String json = objectMapper.writeValueAsString(userRegisterRequestDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }
}