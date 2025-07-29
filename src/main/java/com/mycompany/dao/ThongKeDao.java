/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

/**
 *
 * @author VITHANH
 */

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import com.mycompany.dao.JDBCConnection;

public class ThongKeDao {

    public Map<String, Double> getDoanhThuTheoSanPham() {
        Map<String, Double> data = new HashMap<>();

        String sql = "SELECT p.name, SUM(od.quantity * od.price) AS total FROM OrderDetail od JOIN Product p ON od.product_id = p.id JOIN [Order] o ON od.order_id = o.id WHERE o.status = N'Đã thanh toán' GROUP BY p.name";

        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                data.put(rs.getString("name"), rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}

