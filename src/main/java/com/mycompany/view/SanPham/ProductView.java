package com.mycompany.view.SanPham;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.mycompany.dao.ProductDao;
import com.mycompany.model.Product;
import com.mycompany.model.Cart;
import com.mycompany.model.CartItem;
import com.mycompany.service.Admin.CartService;
import com.mycompany.service.Admin.UserService;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.Account.loginform;

public class ProductView extends JPanel {
    public static ProductView instance;

    public ProductView() {
        instance = this;

        // Scroll pane chứa sản phẩm
        JScrollPane scrollPane = new JScrollPane();
        // Tăng tốc độ scroll
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);  // Tăng step mỗi lần cuộn chuột

        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 4, 20, 20));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 245, 245));

        scrollPane.setViewportView(contentPanel);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        ProductDao dao = new ProductDao();
        List<Product> list = dao.getAllProducts();

        for (Product p : list) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(10, 10, 10, 10)
            ));

            JLabel imgLabel;
            String imagePath = p.getImagepath();
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
                imgLabel = new JLabel(new ImageIcon(img));
            } else {
                imgLabel = new JLabel("Không có ảnh");
                imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imgLabel.setPreferredSize(new Dimension(150, 120));
            }
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel name = new JLabel(p.getName());
            name.setFont(new Font("Segoe UI", Font.BOLD, 16));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            name.setForeground(new Color(50, 50, 50));

            JLabel price = new JLabel("Giá: " + p.getPrice() + " đ");
            price.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            price.setForeground(new Color(200, 0, 0));
            price.setAlignmentX(Component.CENTER_ALIGNMENT);

            JTextArea desc = new JTextArea(p.getDescription());
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            desc.setEditable(false);
            desc.setOpaque(false);
            desc.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            desc.setForeground(new Color(90, 90, 90));
            desc.setAlignmentX(Component.CENTER_ALIGNMENT);
            desc.setMaximumSize(new Dimension(180, 60));
            desc.setBorder(null);
            desc.setFocusable(false);

            JButton btnAddToCart = new JButton("Thêm vào giỏ hàng");
            btnAddToCart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            btnAddToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnAddToCart.setBackground(new Color(0, 123, 255));
            btnAddToCart.setForeground(Color.WHITE);
            btnAddToCart.setFocusPainted(false);
            btnAddToCart.addActionListener(e -> addToCart(p));

            card.add(imgLabel);
            card.add(Box.createVerticalStrut(10));
            card.add(name);
            card.add(Box.createVerticalStrut(5));
            card.add(desc);
            card.add(Box.createVerticalStrut(5));
            card.add(price);
            card.add(Box.createVerticalStrut(10));
            card.add(btnAddToCart);

            contentPanel.add(card);
        }

        setBackground(new Color(245, 245, 245));
        revalidate();
        repaint();
    }

    private void addToCart(Product product) {
        if (UserSession.currentUsername == null || UserSession.currentUsername.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng!");

            // Đóng cửa sổ hiện tại
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame) {
                ((JFrame) window).dispose();
            }

            new loginform().setVisible(true);
            return;
        }

        try {
            int userId = new UserService().getUserByUserName(UserSession.currentUsername).getId();
            CartService cartService = new CartService();
            Cart cart = cartService.getOrCreateCart(userId);

            List<CartItem> existingItems = cartService.getItems(cart.getId());
            CartItem matchedItem = existingItems.stream()
                .filter(i -> i.getProductId() == product.getId())
                .findFirst()
                .orElse(null);

            if (matchedItem != null) {
                cartService.updateItemQuantity(matchedItem.getId(), matchedItem.getQuantity() + 1);
            } else {
                CartItem newItem = new CartItem();
                newItem.setCartId(cart.getId());
                newItem.setProductId(product.getId());
                newItem.setQuantity(1);
                cartService.addItem(newItem);
            }

            JOptionPane.showMessageDialog(this, "Đã thêm vào giỏ hàng!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm vào giỏ hàng!");
        }
    }

    public void reloadData() {
        removeAll();
        revalidate();
        repaint();
        new ProductView(); // tạo lại UI
    }
}
