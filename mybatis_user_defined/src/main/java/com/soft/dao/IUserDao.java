package com.soft.dao;

import com.soft.pojo.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();

    User findByCondition();
}
