/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.view.Cart;

/**
 *
 * @author VITHANH
 */
import com.mycompany.model.CartItem;
import com.mycompany.model.Product;
import com.mycompany.service.Admin.CartService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Cart extends JPanel {
    private JTable tblCart;
    private JLabel lblTotal;
    private JButton btnCheckout;
    private JButton btnDelete;

    private CartService cartService = new CartService();

    public Cart(int userId) {
        initComponents();
        loadCartData(userId);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel lblTitle = new JLabel("GIỎ HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng giỏ hàng
        tblCart = new JTable(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Tên sản phẩm", "Số lượng", "Giá tiền", "Thành tiền"}
        ) {
            final Class[] types = new Class [] {
                String.class, Integer.class, Float.class, Float.class
            };
            final boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblCart);
        add(scrollPane, BorderLayout.CENTER);

        // Panel dưới: tổng tiền + nút
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        lblTotal = new JLabel("Tổng cộng: ");
        btnDelete = new JButton("Xóa sản phẩm");
        btnCheckout = new JButton("Thanh toán");

        bottomPanel.add(lblTotal);
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnCheckout);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadCartData(int userId) {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        model.setRowCount(0);

        List<CartItem> items = cartService.getCartItemsByUserId(userId);
        float total = 0;

        for (CartItem item : items) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            float price = (float) p.getPrice();
            float subTotal = qty * price;
            total += subTotal;

            model.addRow(new Object[] {
                p.getName(),
                qty,
                price,
                subTotal
            });
        }

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        lblTotal.setText("Tổng cộng: " + nf.format(total));
    }

    // Getter
    public JTable getTblCart() {
        return tblCart;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public JButton getBtnCheckout() {
        return btnCheckout;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }
}

