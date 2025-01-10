package com.rockchen.springbootshopmall.dao.repository;

import com.rockchen.springbootshopmall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(" SELECT u FROM User u WHERE u.email = :email")
    User findByEmail( @Param("email") String email );

}
