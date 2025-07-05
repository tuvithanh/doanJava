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
    public void insert(User user){ 
        userDao.addUser(user);
    }
    public void update(User user){
        userDao.updateUser(user);
    }
    public void delete(User user){
        userDao.deleteUser(user);
    }
    public void deleteById(int id){
        userDao.deleteUserByID(id);
    }
    public User getUserByID(int id){
        return userDao.getUserByID(id);
    }
    public User getUserByUserName(String username){
        return userDao.getUserByUsername(username);
    }
}   
