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

    public ProductService() {
        productDao = new ProductDao();
    }

    public List<Product> getAllProduct() {
        return productDao.getAllProducts();  // sửa chính tả chỗ này
    }

    public void addProduct(Product pro) {
        productDao.addProduct(pro);
    }

    public void deleteProductByID(int id) {
        productDao.deleteProductByID(id);
    }

    public Product getProductById(int id) {
        return productDao.getProductByID(id);
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }
    public List<Product> searchProducts(String kw){
        return productDao.searchProducts(kw);
    }
}
