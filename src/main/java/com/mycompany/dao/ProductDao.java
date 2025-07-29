package com.mycompany.dao;

import com.mycompany.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "SELECT * FROM Product";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setCateid(resultSet.getInt("cateid"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setImagepath(resultSet.getString("imagepath"));
                product.setRating(resultSet.getFloat("rating"));
                product.setSoldCount(resultSet.getInt("sold_count"));
                product.setLiked(resultSet.getBoolean("is_liked"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public void addProduct(Product pro) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "INSERT INTO Product (cateid, name, description, price, imagepath, rating, sold_count, is_liked) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pro.getCateid());
            ps.setString(2, pro.getName());
            ps.setString(3, pro.getDescription());
            ps.setDouble(4, pro.getPrice());
            ps.setString(5, pro.getImagepath());
            ps.setFloat(6, pro.getRating());
            ps.setInt(7, pro.getSoldCount());
            ps.setBoolean(8, pro.isLiked());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getProductByID(int id) {
        Product product = null;
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "SELECT * FROM Product WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setCateid(rs.getInt("cateid"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImagepath(rs.getString("imagepath"));
                product.setRating(rs.getFloat("rating"));
                product.setSoldCount(rs.getInt("sold_count"));
                product.setLiked(rs.getBoolean("is_liked"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public void updateProduct(Product pro) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "UPDATE Product SET name = ?, description = ?, price = ?, imagepath = ?, cateid = ?, rating = ?, sold_count = ?, is_liked = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pro.getName());
            ps.setString(2, pro.getDescription());
            ps.setDouble(3, pro.getPrice());
            ps.setString(4, pro.getImagepath());
            ps.setInt(5, pro.getCateid());
            ps.setFloat(6, pro.getRating());
            ps.setInt(7, pro.getSoldCount());
            ps.setBoolean(8, pro.isLiked());
            ps.setInt(9, pro.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductByID(int id) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sqlDeleteCartItem = "DELETE FROM CartItem WHERE product_id = ?";
        String sqlDeleteFavorite = "DELETE FROM Favorite WHERE product_id = ?";
        String sqlDeleteProduct = "DELETE FROM Product WHERE id = ?";

        try {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Xóa trong bảng CartItem
            PreparedStatement psCart = connection.prepareStatement(sqlDeleteCartItem);
            psCart.setInt(1, id);
            psCart.executeUpdate();

            // 2. Xóa trong bảng Favorite
            PreparedStatement psFav = connection.prepareStatement(sqlDeleteFavorite);
            psFav.setInt(1, id);
            psFav.executeUpdate();

            // 3. Xóa sản phẩm chính
            PreparedStatement psProd = connection.prepareStatement(sqlDeleteProduct);
            psProd.setInt(1, id);
            psProd.executeUpdate();

            connection.commit(); // Thành công thì commit

        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // Khôi phục tự động commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Product> getProductsByCategory(int cateId) {
        List<Product> products = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "SELECT * FROM Product WHERE cateid = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cateId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setCateid(rs.getInt("cateid"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImagepath(rs.getString("imagepath"));
                product.setRating(rs.getFloat("rating"));
                product.setSoldCount(rs.getInt("sold_count"));
                product.setLiked(rs.getBoolean("is_liked"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    public List<Product> searchProducts(String keyword) {
        List<Product> results = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        try {
            String sql = "SELECT * FROM Product WHERE name LIKE ? OR description LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setCateid(rs.getInt("cateid"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImagepath(rs.getString("imagepath"));
                product.setRating(rs.getFloat("rating"));
                product.setSoldCount(rs.getInt("sold_count"));
                product.setLiked(rs.getBoolean("is_liked"));

                results.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}
