/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author VITHANH
 */
public class JDBCConnection {
    public static Connection getJDBCConnection() {
        Connection connection = null;
        try {
            // Đảm bảo driver đã được tải
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Cấu hình chuỗi kết nối
            String url = "jdbc:sqlserver://localhost:1433;databaseName=doanJava;encrypt=false;trustServerCertificate=true";
            String userName = "sa";
            String password = "123123";

            // Tạo kết nối
            connection = DriverManager.getConnection(url, userName, password);
            
            return connection; // Trả về kết nối khi thành công
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối SQL.");
            e.printStackTrace();
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            System.out.println("Lỗi không xác định.");
            e.printStackTrace();
        }
        return null; // Trả về null nếu có lỗi
    }

    public static void main(String[] args) {
        // Kiểm tra kết nối
        Connection conn = getJDBCConnection();
        if (conn != null) {
            System.out.println("Kết nối thành công!");
            try {
                conn.close(); // Đóng kết nối khi không sử dụng
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Kết nối thất bại.");
        }
    }
    
}
