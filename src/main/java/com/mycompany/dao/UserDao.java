package com.mycompany.dao;

import com.mycompany.model.User;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "Select * from [User]";

        try {
            PreparedStatement preparestaStatement = connection.prepareStatement(sql);
            ResultSet rs = preparestaStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone")); // CHỈNH Ở ĐÂY
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAbout(rs.getString("about"));
                String roleStr = rs.getString("role");
                if (roleStr != null && !roleStr.isEmpty()) {
                    user.setRole(roleStr.charAt(0));
                }
                user.setFavorites(rs.getString("favorites"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void addUser(User user) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "INSERT INTO [User](name, phone, username, password, email, address, about, role, favorites) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone()); // CHỈNH Ở ĐÂY
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getAbout());
            preparedStatement.setString(8, String.valueOf(user.getRole()));
            preparedStatement.setString(9, user.getFavorites() != null ? user.getFavorites() : "");
            int rs = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateUser(User user) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "Update [User] set name = ?, phone = ?, username = ?, password = ?, email = ?, address = ?, about = ?, role = ?, favorites = ? where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getAbout());
            preparedStatement.setString(8, String.valueOf(user.getRole()));
            preparedStatement.setString(9, user.getFavorites() != null ? user.getFavorites() : "");
            preparedStatement.setInt(10, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteUser(User user) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "DELETE From [User] where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserByID(int id) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "DELETE From [User] where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByID(int id) {
        Connection connection = JDBCConnection.getJDBCConnection();
        User user = null;

        String sql = "SELECT * FROM [User] WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone")); // CHỈNH Ở ĐÂY
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAbout(rs.getString("about"));

                String roleStr = rs.getString("role");
                if (roleStr != null && !roleStr.isEmpty()) {
                    user.setRole(roleStr.charAt(0));
                }

                user.setFavorites(rs.getString("favorites"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByUsername(String username) {
        User user = null;
        Connection conn = JDBCConnection.getJDBCConnection();
        String sql = "Select * from [User] where username = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone")); // CHỈNH Ở ĐÂY
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAbout(rs.getString("about"));

                String roleStr = rs.getString("role");
                if (roleStr != null && !roleStr.isEmpty()) {
                    user.setRole(roleStr.charAt(0));
                }

                user.setFavorites(rs.getString("favorites"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean changePassword(int userId, String newPassword) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "UPDATE [User] SET password = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
