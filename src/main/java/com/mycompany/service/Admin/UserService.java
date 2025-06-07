/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.Admin;

import com.mycompany.dao.UserDao;
import com.mycompany.model.User;
import java.util.List;
/**
 *
 * @author VITHANH
 */
public class UserService {
    private UserDao userDao;
    
    public UserService(){
        userDao = new UserDao();
    }
    public List<User> getAllUser(){
        return userDao.getAllUsers();
    }
}
