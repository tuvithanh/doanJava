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
import com.mycompany.service.Admin.CartService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.function.Consumer;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private boolean isPushed;
    private int row;
    private List<CartItem> items;
    private Runnable onUpdate;
    private CartService cartService;

    public ButtonEditor(JCheckBox checkBox, List<CartItem> items, Runnable onUpdate, CartService cartService) {
        super(checkBox);
        this.items = items;
        this.onUpdate = onUpdate;
        this.cartService = cartService;

        button = new JButton("Xóa");
        button.setOpaque(true);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.RED);

        button.addActionListener((ActionEvent e) -> {
            if (isPushed && row < items.size()) {
                CartItem item = items.get(row);
                int confirm = JOptionPane.showConfirmDialog(button, "Xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    cartService.deleteItem(item.getId());
                    onUpdate.run();
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.row = row;
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        isPushed = false;
        return "Xóa";
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}

