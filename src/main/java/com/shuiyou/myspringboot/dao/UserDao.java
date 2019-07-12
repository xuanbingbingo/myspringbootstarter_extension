package com.shuiyou.myspringboot.dao;

import com.shuiyou.myspringboot.entity.User;

import java.util.List;


public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAllUser();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
