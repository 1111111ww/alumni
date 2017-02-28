package com.university.alumni.application.impl;

import com.university.alumni.application.UserService;
import com.university.alumni.dao.UserDao;
import com.university.alumni.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wm on 2017/1/19.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;


    @Override
    @Transactional
    public User addUser(String name, String age) {
        return userDao.addUser(name, age);
    }
}
