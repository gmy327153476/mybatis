package com.soft.dao;

import com.soft.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserDao {
    List<User> findAll();

    User findByCondition(User user);
}
