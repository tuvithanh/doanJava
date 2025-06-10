/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.Admin;


import com.mycompany.dao.ProductDao;
import com.mycompany.model.Product;
import java.util.List;
/**
 *
 * @author VITHANH
 */
public class ProductService {
    private ProductDao productDao;
    
    public ProductService(){
        productDao = new ProductDao();
    }
    public List<Product> getAllProduct(){
        return productDao.getAllProcduct();
    }
}
