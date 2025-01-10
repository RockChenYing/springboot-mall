//package com.rockchen.springbootshopmall.dao.impl;
//
//import com.rockchen.springbootshopmall.dao.UserDao;
//import com.rockchen.springbootshopmall.dto.UserRegisterRequestDto;
//import com.rockchen.springbootshopmall.model.User;
//import com.rockchen.springbootshopmall.rowmapper.UserRowMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class UserDaoImpl implements UserDao {
//
//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @Override
//    public User getUserById(Integer userId) {
//
//        String sql = "SELECT user_id, email, password, created_date, last_modified_date" +
//                " FROM user WHERE user_id = :userId"; // Jdbc寫語法記得空格喔
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId",userId);
//
//        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
//
//        if(userList.size() > 0){
//            return userList.get(0);
//        } else{
//            return null;
//        }
//    }
//
//    @Override
//    public User getUserByEmail(String email) {
//        String sql = "SELECT user_id, email, password, created_date, last_modified_date " +
//                "FROM user WHERE email = :email";
//        Map<String, Object> map = new HashMap<>();
//        map.put("email", email);
//
//        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
//
////        if(userList.size() > 0){
////            return userList.get(0);
////        } else {
////            return null;
////        }
//        /*  stream().findFirst()：尋找列表中的第一個元素。  如果列表為空，則返回 Optional.empty()。
//            orElse(null)：如果 Optional 中沒有值，則返回 null。 */
//        return userList.stream()
//                       .findFirst()
//                       .orElse(null);
//    }
//
//    @Override
//    public Integer createUser(UserRegisterRequestDto userRegisterRequestDto) {
//        String sql = "INSERT INTO user(email, password, created_date, last_modified_date)" +
//                "VALUES (:email, :password, :createdDate, :lastModifiedDate)";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("email", userRegisterRequestDto.getEmail());
//        map.put("password", userRegisterRequestDto.getPassword());
//
//        Date nowDate = new Date();
//        map.put("createdDate", nowDate);
//        map.put("lastModifiedDate", nowDate);
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
//
//        int userId = keyHolder.getKey().intValue();
//
//        return userId;
//    }
//}
