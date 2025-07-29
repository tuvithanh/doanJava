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
    public String changeUserPassword(String username, String oldPassword, String newPassword, String confirmPassword) {
        User user = getUserByUserName(username);
        if (user == null) {
            return "Tài khoản không tồn tại.";
        }

        // Kiểm tra mật khẩu cũ
        if (!user.getPassword().equals(oldPassword)) {
            return "Mật khẩu hiện tại không đúng.";
        }

        // Kiểm tra mật khẩu mới và xác nhận
        if (!newPassword.equals(confirmPassword)) {
            return "Xác nhận mật khẩu mới không khớp.";
        }

        boolean updated = userDao.changePassword(user.getId(), newPassword);
        if (updated) {
            return null; // Thành công
        } else {
            return "Có lỗi xảy ra khi cập nhật mật khẩu.";
        }
    }

    public boolean checkPassword(String username, String password) {
        User user = getUserByUserName(username);
        return user != null && user.getPassword().equals(password);
    }

    public void updatePassword(String username, String newPassword) {
        User user = getUserByUserName(username);
        if (user != null) {
            user.setPassword(newPassword);
            updateUserInfo(user);
        }
    }

    public String updateUserInfo(User user) {
        try {
            // Kiểm tra user có tồn tại không
            if (user == null) {
                return "Thông tin người dùng không hợp lệ";
            }

            // Kiểm tra user có trong database không
            User existingUser = userDao.getUserByID(user.getId());
            if (existingUser == null) {
                return "Người dùng không tồn tại trong hệ thống";
            }

            // Thực hiện cập nhật
            userDao.updateUser(user);

            // Kiểm tra lại xem cập nhật có thành công không
            User updatedUser = userDao.getUserByID(user.getId());
            if (updatedUser == null || 
                !updatedUser.getName().equals(user.getName()) || 
                !updatedUser.getPhone().equals(user.getPhone())) {
                return "Cập nhật thông tin không thành công";
            }

            return null; // null nghĩa là thành công
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi hệ thống khi cập nhật thông tin: " + e.getMessage();
        }
    }


}   
