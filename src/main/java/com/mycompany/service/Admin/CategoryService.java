/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.Admin;

import com.mycompany.dao.CategoryDao;
import com.mycompany.model.Category;
import java.util.List;
/**
 *
 * @author VITHANH
 */
public class CategoryService {
    private CategoryDao caterogyDao;
    
    public CategoryService(){
        caterogyDao = new CategoryDao();
    }
    public List<Category> getAllCategory(){
        return caterogyDao.getAllCategory();
    }
    public void deleteCategoryByID(int id){
        caterogyDao.deleteCategoryByID(id);
    }
    public void addCategory(Category cate){
        caterogyDao.addCategory(cate);
    }
    public Category getCategoryByID(int id){
        return caterogyDao.getCategoryByID(id);
    }
    public void updateCategory(Category cate){
        caterogyDao.updateCategory(cate);
    }
    public int getIdByName(String s){
        return caterogyDao.getIdByName(s);
    }
    public Category getCategoryByName(String name) {
    for (Category c : getAllCategory()) {
        if (c.getName().equalsIgnoreCase(name)) {
            return c;
        }
    }
    return null;
}

}
