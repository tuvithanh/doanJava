/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.Admin;

/**
 *
 * @author VITHANH
 */
import com.mycompany.dao.CartDao;
import com.mycompany.dao.JDBCConnection;
import com.mycompany.model.Cart;
import com.mycompany.model.CartItem;
import java.sql.Connection;
import com.mycompany.model.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private final CartDao cartDao = new CartDao();

    public Cart getOrCreateCart(int userId) {
        Cart cart = cartDao.getCartByUserId(userId);
        if (cart == null) {
            int cartId = cartDao.createCart(userId);
            cart = new Cart();
            cart.setId(cartId);
            cart.setUserId(userId);
        }
        return cart;
    }

    public List<CartItem> getItems(int cartId) {
        return cartDao.getCartItems(cartId);
    }

    public void addItem(CartItem item) {
        cartDao.addCartItem(item);
    }

    public void updateItemQuantity(int itemId, int quantity) {
        cartDao.updateCartItemQuantity(itemId, quantity);
    }

    public void deleteItem(int itemId) {
        cartDao.deleteCartItem(itemId);
    }

    public void clearCart(int cartId) {
        cartDao.clearCart(cartId);
    }
    public List<CartItem> getCartItemsByUserId(int userId) {
    List<CartItem> items = new ArrayList<>();

        String sql = """
            SELECT ci.id AS cart_item_id, ci.cart_id, ci.product_id, ci.quantity,
                   p.id AS pid, p.name, p.price, p.description, p.imagepath, p.cateid
            FROM CartItem ci
            JOIN Cart c ON ci.cart_id = c.id
            JOIN Product p ON ci.product_id = p.id
            WHERE c.user_id = ?
        """;

        try (Connection conn = JDBCConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));

                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setImagepath(rs.getString("imagepath"));
                product.setCateid(rs.getInt("cateid"));

                item.setProduct(product); // cần getter/setter này trong CartItem
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}



