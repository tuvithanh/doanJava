/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.Admin;

/**
 *
 * @author VITHANH
 */
import com.mycompany.dao.FavoriteDao;
import com.mycompany.model.Favorite;

import java.util.List;

public class FavoriteService {
    private final FavoriteDao favoriteDao = new FavoriteDao();

    public boolean isFavorite(int userId, int productId) {
        return favoriteDao.isFavorite(userId, productId);
    }

    public boolean toggleFavorite(int userId, int productId) {
        if (isFavorite(userId, productId)) {
            favoriteDao.removeFavorite(userId, productId);
            return false; // đã xóa khỏi yêu thích
        } else {
            favoriteDao.addFavorite(new Favorite(userId, productId));
            return true; // đã thêm vào yêu thích
        }
    }
    public List<Favorite> getFavoritesByUserId(int userId) {
        return favoriteDao.getFavoritesByUserId(userId);
    }
}

