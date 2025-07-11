/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.util.Date;

/**
 *
 * @author VITHANH
 */


public class Favorite {
    private int id;
    private int userId;
    private int productId;
    private Date likedAt;
    
    public Favorite(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public Date getLikedAt() { return likedAt; }
    public void setLikedAt(Date likedAt) { this.likedAt = likedAt; }
    
}

