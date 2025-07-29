package com.mycompany.view.Admin.QLPRODUCT;

import com.mycompany.model.Product;
import com.mycompany.model.Category;
import com.mycompany.service.Admin.ProductService;
import com.mycompany.service.Admin.CategoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class EditProduct extends JFrame {
    private JTextField txtName, txtDescription, txtPrice;
    private JComboBox<Category> cbCategory;
    private JLabel lblImage;
    private JButton btnChooseImage, btnSave;

    private String imagePath = null;
    private Product product;

    public EditProduct(int productId) {
        ProductService productService = new ProductService();
        this.product = productService.getProductById(productId);
        if (product == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm với ID: " + productId);
            dispose();
            return;
        }
        initComponents();
        loadProductData();
    }

    private void initComponents() {
        setTitle("Chỉnh sửa sản phẩm");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblName = new JLabel("Tên:");
        JLabel lblDescription = new JLabel("Mô tả:");
        JLabel lblPrice = new JLabel("Giá:");
        JLabel lblCategory = new JLabel("Danh mục:");
        JLabel lblImageTitle = new JLabel("Ảnh:");

        txtName = new JTextField(20);
        txtDescription = new JTextField(20);
        txtPrice = new JTextField(20);
        cbCategory = new JComboBox<>();
        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(200, 200));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        btnChooseImage = new JButton("Chọn ảnh");
        btnSave = new JButton("Lưu");

        // Load categories
        List<Category> categories = new CategoryService().getAllCategory();
        for (Category c : categories) cbCategory.addItem(c);

        // Layout
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; add(lblName, gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(lblDescription, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(txtDescription, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(lblPrice, gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(txtPrice, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(lblCategory, gbc);
        gbc.gridx = 1; gbc.gridy = 3; add(cbCategory, gbc);

        gbc.gridx = 0; gbc.gridy = 4; add(lblImageTitle, gbc);
        gbc.gridx = 1; gbc.gridy = 4; add(lblImage, gbc);

        gbc.gridx = 1; gbc.gridy = 5; add(btnChooseImage, gbc);
        gbc.gridx = 1; gbc.gridy = 6; add(btnSave, gbc);

        // Button actions
        btnChooseImage.addActionListener(e -> chooseImage());
        btnSave.addActionListener(e -> saveProduct());

        setVisible(true);
    }

    private void loadProductData() {
        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        txtPrice.setText(String.valueOf(product.getPrice()));

        for (int i = 0; i < cbCategory.getItemCount(); i++) {
            if (cbCategory.getItemAt(i).getId() == product.getCateid()) {
                cbCategory.setSelectedIndex(i);
                break;
            }
        }

        if (product.getImagepath() != null && !product.getImagepath().isEmpty()) {
            File imgFile = new File(product.getImagepath());
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(product.getImagepath());
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
                imagePath = product.getImagepath();
            }
        }
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdir();

                File destFile = new File(destDir, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imagePath = destFile.getPath();

                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi chọn ảnh");
            }
        }
    }

    private void saveProduct() {
        try {
            String name = txtName.getText();
            String description = txtDescription.getText();
            double price = Double.parseDouble(txtPrice.getText());
            Category selectedCategory = (Category) cbCategory.getSelectedItem();

            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCateid(selectedCategory.getId());
            product.setImagepath(imagePath);

            new ProductService().updateProduct(product);
            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu sản phẩm!");
        }
    }
}
