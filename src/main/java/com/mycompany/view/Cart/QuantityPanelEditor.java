package com.mycompany.view.Cart;


import com.mycompany.CredentialManager.CredentialManager;
import com.mycompany.model.CartItem;
import com.mycompany.service.Admin.CartService;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.Account.loginform;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author VITHANH
 */
class QuantityPanelEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
    private JPanel panel;
    private JLabel lblQuantity;
    private int quantity;
    private List<CartItem> items;
    private Runnable onUpdate;
    private int currentRow;
    private CartService cartService;

    public QuantityPanelEditor(List<CartItem> items, Runnable onUpdate, CartService cartService) {
        this.items = items;
        this.onUpdate = onUpdate;
        this.cartService = cartService;

        panel = new JPanel(new FlowLayout());

        JButton btnMinus = new JButton("-");
        JButton btnPlus = new JButton("+");
        lblQuantity = new JLabel();

        btnMinus.addActionListener(e -> {
            if (quantity > 1) {
                quantity--;
                updateQuantity();
            } else {
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    cartService.deleteItem(items.get(currentRow).getId());
                    onUpdate.run();
                }
                else{
                    onUpdate.run();
                }
            }
        });

        btnPlus.addActionListener(e -> {
            quantity++;
            updateQuantity();
        });

        panel.add(btnMinus);
        panel.add(lblQuantity);
        panel.add(btnPlus);
    }

    private void updateQuantity() {
        lblQuantity.setText(String.valueOf(quantity));
        CartItem item = items.get(currentRow);
        cartService.updateItemQuantity(item.getId(), quantity);
        onUpdate.run();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        currentRow = row;
        quantity = (int) value;
        lblQuantity.setText(String.valueOf(quantity));
        return panel;
    }

    public Object getCellEditorValue() {
        return quantity;
    }
}


