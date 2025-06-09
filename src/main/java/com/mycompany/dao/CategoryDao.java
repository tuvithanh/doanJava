/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import java.util.ArrayList;
import com.mycompany.model.Category;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author VITHANH
 */
public class CategoryDao {
    public List<Category> getAllCategory(){
        List<Category> categories = new ArrayList<Category>();
        
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "Select * from Category";
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Category cate = new Category();
                
                cate.setId(rs.getInt("id"));
                cate.setName(rs.getString("name"));
                
                categories.add(cate);
            }
            return categories;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return categories;
    }
    public void addCategory(Category cate){
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "Insert into Category (name) values(?)";
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cate.getName());
            
            
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void updateCategory(Category cate){
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "Update Category Set name = ? where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cate.getName());
            
            preparedStatement.setInt(2, cate.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteCategoryByID(int id){
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "Delete from Category where id = ?";
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public Category getCategoryByID(int id){
        Category cate = new Category();
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "Select * from Category where id = ?";
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            
            while(rs.next()){
                cate.setId(rs.getInt("id"));
                cate.setName(rs.getString("name"));
            }  
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return cate;
    }
}
