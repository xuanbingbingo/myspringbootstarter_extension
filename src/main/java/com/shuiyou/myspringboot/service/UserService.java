package com.shuiyou.myspringboot.service;

import com.shuiyou.myspringboot.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public User getUserById(int userId);

    public List<User> selectAllUser(int pageNum, int pageSize);

    boolean addUser(User record);

    public void printTest();

}