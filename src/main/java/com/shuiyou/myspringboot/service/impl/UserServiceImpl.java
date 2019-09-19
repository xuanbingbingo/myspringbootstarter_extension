package com.shuiyou.myspringboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.shuiyou.myspringboot.dao.UserDao;
import com.shuiyou.myspringboot.entity.User;
import com.shuiyou.myspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;


    public User getUserById(int userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> selectAllUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDao.selectAllUser();
        return userList;
    }

    public boolean addUser(User record){
        boolean result = false;
        try {
            userDao.insertSelective(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void printTest() {
        System.out.println("我擦咧，执行了！");
    }

}