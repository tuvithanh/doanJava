/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.view.Cart;


import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class QuantityPanelRenderer extends JPanel implements TableCellRenderer {

    private JButton btnMinus;
    private JLabel lblQuantity;
    private JButton btnPlus;

    public QuantityPanelRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        btnMinus = new JButton("-");
        lblQuantity = new JLabel("1", SwingConstants.CENTER);
        btnPlus = new JButton("+");

        btnMinus.setEnabled(false);
        btnPlus.setEnabled(false);

        add(btnMinus);
        add(lblQuantity);
        add(btnPlus);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (value instanceof Integer) {
            lblQuantity.setText(String.valueOf(value));
        }
        return this;
    }
}

