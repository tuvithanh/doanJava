package com.mycompany.view.Cart;

import com.mycompany.model.CartItem;
import com.mycompany.model.Product;
import com.mycompany.service.Admin.CartService;
import com.mycompany.service.Admin.UserService;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.Thanhtoan.Thanhtoan;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;

public class Cart extends JPanel {
    private JTable tblCart;
    private JLabel lblTotal;
    private JCheckBox selectAllCheckBox;
    private CartService cartService = new CartService();
    private List<CartItem> items;
    private List<CartItem> itemsChecked = new ArrayList<>();
    private JPanel contentPanel;

    public Cart(int userId, JPanel contentPanel) {
        this.contentPanel = contentPanel;
        initComponents();
        loadCartData(userId);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("GIỎ HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.CENTER);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectAllCheckBox = new JCheckBox("Chọn tất cả");
        checkBoxPanel.add(selectAllCheckBox);
        topPanel.add(checkBoxPanel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        tblCart = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblCart);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Tổng cộng: ");
        bottomPanel.add(lblTotal);

        JButton btnCheckout = new JButton("Thanh toán");
        btnCheckout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCheckout.setBackground(new Color(0, 153, 0));
        btnCheckout.setForeground(Color.WHITE);

        btnCheckout.addActionListener(e -> {
            if (itemsChecked.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để thanh toán.");
                return;
            }

            int userId = new UserService().getUserByUserName(UserSession.currentUsername).getId();
            contentPanel.removeAll();
            contentPanel.add(new Thanhtoan(userId, itemsChecked, contentPanel), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        bottomPanel.add(btnCheckout);
        add(bottomPanel, BorderLayout.SOUTH);

        selectAllCheckBox.addActionListener(e -> {
            boolean selected = selectAllCheckBox.isSelected();
            for (int i = 0; i < tblCart.getRowCount(); i++) {
                tblCart.setValueAt(selected, i, 0);
            }
        });
    }

    private void loadCartData(int userId) {
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {"", "Ảnh", "Tên sản phẩm", "Số lượng", "Giá tiền", "Thành tiền", "Xóa"}
        ) {
            final Class<?>[] columnTypes = new Class<?>[] {
                Boolean.class, JLabel.class, String.class, Integer.class, Float.class, Float.class, JButton.class
            };
            final boolean[] canEdit = new boolean[] {
                true, false, false, true, false, false, true
            };

            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        tblCart.setModel(model);
        tblCart.setRowHeight(80);

        items = cartService.getCartItemsByUserId(userId);

        for (CartItem item : items) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            float price = (float) p.getPrice();
            float subTotal = qty * price;

            JLabel imgLabel;
            File imgFile = new File(p.getImagepath());
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                imgLabel = new JLabel(new ImageIcon(img));
            } else {
                imgLabel = new JLabel("Không có ảnh");
                imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }

            model.addRow(new Object[] {
                false, imgLabel, p.getName(), qty, price, subTotal, "Xóa"
            });
        }

        tblCart.getColumnModel().getColumn(3).setCellEditor(new QuantityPanelEditor(items, this::reload, cartService));
        tblCart.getColumnModel().getColumn(3).setCellRenderer(new QuantityPanelRenderer());

        tblCart.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tblCart.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), items, this::reload, cartService));

        tblCart.getColumnModel().getColumn(1).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> (Component) value);

        model.addTableModelListener(e -> {
            float total = 0;
            itemsChecked.clear();
            for (int i = 0; i < tblCart.getRowCount(); i++) {
                Boolean isChecked = (Boolean) tblCart.getValueAt(i, 0);
                if (isChecked != null && isChecked) {
                    itemsChecked.add(items.get(i));
                    Float subTotal = (Float) tblCart.getValueAt(i, 5);
                    if (subTotal != null) total += subTotal;
                }
            }
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            lblTotal.setText("Tổng cộng: " + nf.format(total));
            selectAllCheckBox.setSelected(itemsChecked.size() == tblCart.getRowCount());
        });
    }

    private void reload() {
        removeAll();
        initComponents();
        loadCartData(new UserService().getUserByUserName(UserSession.currentUsername).getId());
        revalidate();
        repaint();
    }
}
