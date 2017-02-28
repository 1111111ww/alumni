package com.university.alumni.dao.impl;

import com.university.alumni.dao.UserDao;
import com.university.alumni.entity.User;
import com.university.common.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by wm on 2017/1/19.
 */
@Repository
public class UserDaoImpl  extends BaseDaoImpl implements UserDao {

    @Override
    public User addUser(String name, String age) {
        User user=new User();
        user.setName("娜娜");
        user.setAge("19");
        save(user);
        return user;
    }
}
