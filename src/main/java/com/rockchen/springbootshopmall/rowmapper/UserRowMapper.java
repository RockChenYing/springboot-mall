//package com.rockchen.springbootshopmall.rowmapper;
//
//import com.mysql.cj.result.Row;
//import com.rockchen.springbootshopmall.model.User;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//// 將資料庫的結果轉換成User object
//public class UserRowMapper implements RowMapper<User> {
//    @Override
//    public User mapRow(ResultSet resultSet, int i) throws SQLException {
//        User user = new User();
//        user.setUserId(resultSet.getInt("user_id"));
//        user.setEmail(resultSet.getString("email"));
//        user.setPassword(resultSet.getString("password"));
//        user.setCreatedDate(resultSet.getTimestamp("created_date"));
//        user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
//
//        return user;
//    }
//}
