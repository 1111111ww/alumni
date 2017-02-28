package com.university.alumni.admin.web.controller;

import com.university.alumni.application.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wm on 2017/1/19.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/addUser")
    public void addUser(String name , String age, HttpServletRequest request, HttpServletResponse response){
        userService.addUser(name, age);
    }
}
