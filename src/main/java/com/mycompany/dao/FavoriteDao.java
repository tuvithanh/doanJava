/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

/**
 *
 * @author VITHANH
 */
import com.mycompany.dao.JDBCConnection;
import com.mycompany.model.Favorite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDao {
    public boolean isFavorite(int userId, int productId) {
        String sql = "SELECT * FROM Favorite WHERE user_id = ? AND product_id = ?";
        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addFavorite(Favorite f) {
        String sql = "INSERT INTO Favorite(user_id, product_id) VALUES (?, ?)";
        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, f.getUserId());
            ps.setInt(2, f.getProductId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFavorite(int userId, int productId) {
        String sql = "DELETE FROM Favorite WHERE user_id = ? AND product_id = ?";
        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Favorite> getFavoritesByUserId(int userId) {
        List<Favorite> list = new ArrayList<>();
        String sql = "SELECT * FROM Favorite WHERE user_id = ?";

        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Favorite fav = new Favorite(rs.getInt("user_id"), rs.getInt("product_id"));
                fav.setId(rs.getInt("id"));
                fav.setLikedAt(rs.getTimestamp("liked_at")); // Nếu cột có kiểu DATETIME
                list.add(fav);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
