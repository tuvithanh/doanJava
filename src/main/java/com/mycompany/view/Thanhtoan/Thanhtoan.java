package com.mycompany.view.Thanhtoan;

import com.mycompany.model.CartItem;
import com.mycompany.service.Admin.OrderService;
import com.mycompany.view.Cart.Cart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Thanhtoan extends JPanel {
    private final int userId;
    private final List<CartItem> itemsChecked;
    private final JPanel contentPanel;
    private final OrderService orderService = new OrderService();
    private JLabel totalLabel;

    public Thanhtoan(int userId, List<CartItem> itemsChecked, JPanel contentPanel) {
        this.userId = userId;
        this.itemsChecked = itemsChecked;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        initComponents();
    }

    private void initComponents() {
        JLabel title = new JLabel("XÁC NHẬN THANH TOÁN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JTable table = new JTable();
        String[] columnNames = {"Tên sản phẩm", "Số lượng", "Giá", "Thành tiền"};
        Object[][] data = new Object[itemsChecked.size()][4];

        float total = 0;
        for (int i = 0; i < itemsChecked.size(); i++) {
            CartItem item = itemsChecked.get(i);
            float subTotal = (float) (item.getQuantity() * item.getProduct().getPrice());
            data[i][0] = item.getProduct().getName();
            data[i][1] = item.getQuantity();
            data[i][2] = item.getProduct().getPrice();
            data[i][3] = subTotal;
            total += subTotal;
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        table.setEnabled(false);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        totalLabel = new JLabel("Tổng tiền: " + nf.format(total));
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        centerPanel.add(totalLabel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM panel gồm totalLabel + 2 nút
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Panel chứa nút hủy (bên trái)
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancel.setBackground(Color.LIGHT_GRAY);
        btnCancel.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new Cart(userId, contentPanel));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        leftButtonPanel.add(btnCancel);

        // Panel chứa nút thanh toán (bên phải)
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JButton btnPay = new JButton("Thanh toán");
        btnPay.setBackground(new Color(0, 153, 0));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPay.addActionListener(e -> handleCheckout());
        rightButtonPanel.add(btnPay);

        bottomPanel.add(leftButtonPanel, BorderLayout.WEST);
        bottomPanel.add(rightButtonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handleCheckout() {
        try {
            boolean success = orderService.checkout(userId, itemsChecked, "Tiền mặt");

            if (success) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!");

                contentPanel.removeAll();
                contentPanel.add(new Cart(userId, contentPanel));
                contentPanel.revalidate();
                contentPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + e.getMessage());
        }
    }
}
