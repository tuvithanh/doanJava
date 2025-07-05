/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

/**
 *
 * @author VITHANH
 */

import com.mycompany.model.Payment;
import com.mycompany.dao.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {

    // Thêm thanh toán mới
    public int insertPayment(Payment payment) {
        String sql = "INSERT INTO Payment(order_id, payment_method, amount, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getStatus());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Lấy thông tin thanh toán theo order_id
    public Payment getPaymentByOrderId(int orderId) {
        String sql = "SELECT * FROM Payment WHERE order_id = ?";

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setPaidAt(rs.getTimestamp("paid_at").toString());
                payment.setAmount(rs.getDouble("amount"));
                payment.setStatus(rs.getString("status"));
                return payment;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Lấy tất cả các thanh toán (nếu dùng cho admin)
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment ORDER BY paid_at DESC";

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setPaidAt(rs.getTimestamp("paid_at").toString());
                payment.setAmount(rs.getDouble("amount"));
                payment.setStatus(rs.getString("status"));
                list.add(payment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Cập nhật trạng thái thanh toán
    public boolean updatePaymentStatus(int paymentId, String status) {
        String sql = "UPDATE Payment SET status = ? WHERE id = ?";

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, paymentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

