/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Product;
import com.mycompany.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author VITHANH
 */
public class ProductDao {
    public List<Product> getAllProcduct(){
        List<Product> listPro = new ArrayList<>();
        Connection connecttion = JDBCConnection.getJDBCConnection();
        
        String sql = "Select * from Product";
        try{
            PreparedStatement preparedStatement = connecttion.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Product pro = new Product();
                
                pro.setCateid(rs.getInt("cateid"));
                pro.setDescription(rs.getString("description"));
                pro.setName(rs.getString("name"));
                pro.setId(rs.getInt("id"));
                pro.setPrice(rs.getDouble("price"));
                pro.setImagepath(rs.getString("imagepath"));
                
                listPro.add(pro);
                
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return listPro;
    }
    public void addProduct(Product pro){
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "Insert into Product (cateid, name, description, price, imagepath) values(? ,? ,? ,?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pro.getCateid());
            preparedStatement.setString(2, pro.getName());
            preparedStatement.setString(3, pro.getDescription());
            preparedStatement.setDouble(4, pro.getPrice());
            preparedStatement.setString(5, pro.getImagepath());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public int getCateIDByName(String s){
//        Product pro = new Product();
//        Connection con = JDBCConnection.getJDBCConnection();
//        
//        String sql = "Select * from Product where name = ?";
//        
//        try {
//            PreparedStatement preparedStatement = con.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//            preparedStatement.setString(1, s);
//            
//            while(rs.next()){
//                pro.setId(rs.getInt("id"));
//                pro.setCateid(rs.getInt("cateid"));
//                pro.setName(rs.getString("name"));
//                pro.setDescription(rs.getString("description"));
//                pro.setPrice(rs.getDouble("price"));
//                pro.setImagepath(rs.getString("imagepath"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rs;
//    }
    public void deleteProductByID(int id){
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "Delete from Product where id = ?";
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public Product getProductByID(int id){
        Product pro = new Product();
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "Select * from Product where id = ?";
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            
            while(rs.next()){
                pro.setId(rs.getInt("id"));
                pro.setName(rs.getString("name"));
                pro.setDescription(rs.getString("description"));
                pro.setCateid(rs.getInt("cateid"));
                pro.setImagepath(rs.getString("imagepath"));
                pro.setPrice(rs.getDouble("price"));
            }  
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return pro;
    }
    public void updateProduct(Product pro){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "UPDATE Product SET name = ?, description = ?, price = ?, imagepath = ?, cateid = ? WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, pro.getName());
            preparedStatement.setString(2, pro.getDescription());
            preparedStatement.setDouble(3, pro.getPrice());
            preparedStatement.setString(4, pro.getImagepath());
            preparedStatement.setInt(5, pro.getCateid());
            preparedStatement.setInt(6, pro.getId());

            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

}
