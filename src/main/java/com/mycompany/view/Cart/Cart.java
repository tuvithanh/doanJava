package com.mycompany.view.Cart;

import com.mycompany.model.CartItem;
import com.mycompany.model.Product;
import com.mycompany.service.Admin.CartService;
import com.mycompany.service.Admin.UserService;
import com.mycompany.sesion.UserSession.UserSession;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Cart extends JPanel {
    private JTable tblCart;
    private JLabel lblTotal;
    private CartService cartService = new CartService();
    private List<CartItem> items;

    public Cart(int userId) {
        initComponents();
        loadCartData(userId);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("GIỎ HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        tblCart = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblCart);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Tổng cộng: ");
        bottomPanel.add(lblTotal);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadCartData(int userId) {
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Ảnh", "Tên sản phẩm", "Số lượng", "Giá tiền", "Thành tiền", "Xóa"}
        ) {
            final Class<?>[] columnTypes = new Class<?>[] {
                JLabel.class, String.class, Integer.class, Float.class, Float.class, JButton.class
            };
            final boolean[] canEdit = new boolean[] {
                false, false, true, false, false, true
            };

            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        tblCart.setRowHeight(80); // Tăng chiều cao để hiển thị ảnh
        tblCart.setModel(model);
        tblCart.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
                    return (JLabel) value;
                }
        });

        items = cartService.getCartItemsByUserId(userId);
        float total = 0;

        for (CartItem item : items) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            float price = (float) p.getPrice();
            float subTotal = qty * price;
            total += subTotal;

            // ✅ Load ảnh từ imagepath
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
                imgLabel,
                p.getName(),
                qty,
                price,
                subTotal,
                "Xóa"
            });
        }

        // Số lượng
        tblCart.getColumnModel().getColumn(2).setCellEditor(new QuantityPanelEditor(items, this::reload, cartService));
        tblCart.getColumnModel().getColumn(2).setCellRenderer(new QuantityPanelRenderer());

        // Xóa
        tblCart.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tblCart.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), items, this::reload, cartService));

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        lblTotal.setText("Tổng cộng: " + nf.format(total));
    }

    private void reload() {
        removeAll();
        initComponents();
        loadCartData(new UserService().getUserByUserName(UserSession.currentUsername).getId());
        revalidate();
        repaint();
    }
}
