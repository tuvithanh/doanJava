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
}
