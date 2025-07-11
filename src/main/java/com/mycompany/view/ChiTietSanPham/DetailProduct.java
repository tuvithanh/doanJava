package com.mycompany.view.ChiTietSanPham;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.List;
import com.mycompany.model.*;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.service.Admin.*;
import com.mycompany.view.Account.loginform;
import com.mycompany.view.SanPham.ProductView;
import com.mycompany.view.TrangChu.TrangChu;

public class DetailProduct extends JPanel {
    private Color primaryColor = new Color(0, 102, 204);
    private Color secondaryColor = new Color(245, 245, 245);
    private Color accentColor = new Color(255, 87, 34);
    private Color dangerColor = new Color(255, 66, 66);

    public DetailProduct(TrangChu parentFrame, Product product) {
        setLayout(new BorderLayout());
        setBackground(secondaryColor);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // ====== TOP BAR ======
        JPanel topBar = createTopBar(parentFrame);
        add(topBar, BorderLayout.NORTH);

        // ====== MAIN CONTENT ======
        JPanel mainPanel = new JPanel(new BorderLayout(30, 0));
        mainPanel.setBackground(secondaryColor);
        mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // LEFT PANEL - Product Images
        JPanel leftPanel = createImagePanel(product);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // RIGHT PANEL - Product Info
        JPanel rightPanel = createProductInfoPanel(product, parentFrame);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // ====== BOTTOM PANEL - Product Description ======
        JPanel bottomPanel = createDescriptionPanel(product);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopBar(TrangChu parentFrame) {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(secondaryColor);
        
        JButton btnBack = new JButton("← Quay lại danh sách sản phẩm");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setForeground(primaryColor);
        btnBack.setBackground(secondaryColor);
        btnBack.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnBack.setForeground(primaryColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                btnBack.setForeground(primaryColor);
            }
        });
        
        btnBack.addActionListener(e -> {
            JPanel content = parentFrame.getContentPanel();
            content.removeAll();
            content.add(new ProductView());
            content.revalidate();
            content.repaint();
        });

        topBar.add(btnBack);
        return topBar;
    }

    private JPanel createImagePanel(Product product) {
        JPanel leftPanel = new JPanel(new BorderLayout(0, 15));
        leftPanel.setBackground(secondaryColor);
        leftPanel.setPreferredSize(new Dimension(450, 550));

        // Main product image
        JLabel mainImage = new JLabel();
        mainImage.setHorizontalAlignment(JLabel.CENTER);
        mainImage.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        mainImage.setBackground(Color.WHITE);
        mainImage.setOpaque(true);
        
        try {
            ImageIcon icon = new ImageIcon(product.getImagePath());
            Image img = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            mainImage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            mainImage.setText("Không có ảnh sản phẩm");
            mainImage.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            mainImage.setForeground(Color.GRAY);
            mainImage.setHorizontalAlignment(JLabel.CENTER);
        }

        // Thumbnail images
        JPanel thumbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        thumbPanel.setBackground(secondaryColor);
        
        // Sample thumbnails (in real app, use actual product images)
        for (int i = 0; i < 4; i++) {
            JLabel thumb = new JLabel();
            thumb.setPreferredSize(new Dimension(80, 80));
            thumb.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(5, 5, 5, 5)
            ));
            thumb.setBackground(Color.WHITE);
            thumb.setOpaque(true);
            
            try {
                ImageIcon icon = new ImageIcon(product.getImagePath());
                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                thumb.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                thumb.setText("Ảnh");
                thumb.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                thumb.setHorizontalAlignment(JLabel.CENTER);
            }
            
            // Click to change main image
            thumb.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    mainImage.setIcon(thumb.getIcon());
                }
            });
            
            thumbPanel.add(thumb);
        }

        leftPanel.add(mainImage, BorderLayout.CENTER);
        leftPanel.add(thumbPanel, BorderLayout.SOUTH);
        
        return leftPanel;
    }

    private JPanel createProductInfoPanel(Product product, TrangChu parentFrame) {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(secondaryColor);
        rightPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        // Product name
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(new Color(50, 50, 50));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Rating and sales
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        ratingPanel.setBackground(secondaryColor);
        ratingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel ratingLabel = new JLabel("★★★★★");
        ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ratingLabel.setForeground(new Color(255, 153, 0));
        
        JLabel reviewLabel = new JLabel("1,234 đánh giá");
        reviewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        reviewLabel.setForeground(Color.GRAY);
        
        JLabel soldLabel = new JLabel("• Đã bán 5,678");
        soldLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        soldLabel.setForeground(Color.GRAY);
        
        ratingPanel.add(ratingLabel);
        ratingPanel.add(reviewLabel);
        ratingPanel.add(soldLabel);

        // Price
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pricePanel.setBackground(secondaryColor);
        pricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel priceLabel = new JLabel(String.format("%,d₫", (int)product.getPrice()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        priceLabel.setForeground(dangerColor);
        
        JLabel oldPriceLabel = new JLabel(String.format("%,d₫", (int)(product.getPrice() * 1.2)));
        oldPriceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        oldPriceLabel.setForeground(Color.GRAY);
        oldPriceLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        oldPriceLabel.setText("<html><strike>" + oldPriceLabel.getText() + "</strike></html>");
        
        JLabel discountLabel = new JLabel("20%");
        discountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        discountLabel.setForeground(Color.WHITE);
        discountLabel.setBackground(dangerColor);
        discountLabel.setOpaque(true);
        discountLabel.setBorder(new EmptyBorder(2, 6, 2, 6));
        
        pricePanel.add(priceLabel);
        pricePanel.add(oldPriceLabel);
        pricePanel.add(Box.createHorizontalStrut(15));
        pricePanel.add(discountLabel);

        // Shipping info
        JPanel shippingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        shippingPanel.setBackground(secondaryColor);
        shippingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel shippingIcon = new JLabel("🚚");
        JLabel shippingLabel = new JLabel("Miễn phí vận chuyển cho đơn từ 150.000₫");
        shippingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        shippingPanel.add(shippingIcon);
        shippingPanel.add(shippingLabel);

        // Quantity selector
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        quantityPanel.setBackground(secondaryColor);
        quantityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quantityPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        
        JLabel quantityLabel = new JLabel("Số lượng:");
        quantityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setBackground(Color.WHITE);
        spinnerPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        
        JButton decreaseBtn = new JButton("-");
        decreaseBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        decreaseBtn.setPreferredSize(new Dimension(30, 30));
        decreaseBtn.setFocusPainted(false);
        
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantitySpinner.setPreferredSize(new Dimension(50, 30));
        ((JSpinner.DefaultEditor)quantitySpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        JButton increaseBtn = new JButton("+");
        increaseBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        increaseBtn.setPreferredSize(new Dimension(30, 30));
        increaseBtn.setFocusPainted(false);
        
        spinnerPanel.add(decreaseBtn);
        spinnerPanel.add(quantitySpinner);
        spinnerPanel.add(increaseBtn);
        
        JLabel stockLabel = new JLabel("(Còn 99 sản phẩm)");
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        stockLabel.setForeground(Color.GRAY);
        
        quantityPanel.add(quantityLabel);
        quantityPanel.add(spinnerPanel);
        quantityPanel.add(stockLabel);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonPanel.setBackground(secondaryColor);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton addToCartBtn = createActionButton("THÊM VÀO GIỎ HÀNG", accentColor);
        JButton buyNowBtn = createActionButton("MUA NGAY", dangerColor);
        
        buttonPanel.add(addToCartBtn);
        buttonPanel.add(buyNowBtn);

        // Add to right panel
        rightPanel.add(nameLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(ratingPanel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(pricePanel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(shippingPanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(quantityPanel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(buttonPanel);

        // Add event listeners
        addToCartBtn.addActionListener(e -> handleAddToCart(product, parentFrame, (int)quantitySpinner.getValue()));
        buyNowBtn.addActionListener(e -> handleBuyNow(product, parentFrame, (int)quantitySpinner.getValue()));
        
        decreaseBtn.addActionListener(e -> {
            int value = (int)quantitySpinner.getValue();
            if (value > 1) quantitySpinner.setValue(value - 1);
        });
        
        increaseBtn.addActionListener(e -> {
            int value = (int)quantitySpinner.getValue();
            if (value < 100) quantitySpinner.setValue(value + 1);
        });

        return rightPanel;
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private JPanel createDescriptionPanel(Product product) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(30, 0, 0, 0),
            new MatteBorder(1, 0, 0, 0, new Color(230, 230, 230))
        ));

        JLabel titleLabel = new JLabel("CHI TIẾT SẢN PHẨM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JTextArea descArea = new JTextArea(product.getDescription());
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setBorder(new EmptyBorder(0, 0, 20, 0));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(descArea, BorderLayout.CENTER);
        
        return panel;
    }

    private void handleAddToCart(Product product, TrangChu parentFrame, int quantity) {
        if (UserSession.currentUsername == null || UserSession.currentUsername.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "<html><div style='text-align: center;'>Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng</div></html>", 
                "Yêu cầu đăng nhập", 
                JOptionPane.WARNING_MESSAGE);
            parentFrame.dispose();
            new loginform().setVisible(true);
            return;
        }

        try {
            int userId = new UserService().getUserByUserName(UserSession.currentUsername).getId();
            CartService cartService = new CartService();
            Cart cart = cartService.getOrCreateCart(userId);

            List<CartItem> items = cartService.getItems(cart.getId());
            CartItem existing = items.stream()
                    .filter(i -> i.getProductId() == product.getId())
                    .findFirst().orElse(null);

            if (existing != null) {
                cartService.updateItemQuantity(existing.getId(), existing.getQuantity() + quantity);
            } else {
                CartItem item = new CartItem();
                item.setCartId(cart.getId());
                item.setProductId(product.getId());
                item.setQuantity(quantity);
                cartService.addItem(item);
            }

            JOptionPane.showMessageDialog(this, 
                "<html><div style='text-align: center;'>Đã thêm <b>" + product.getName() + "</b><br>vào giỏ hàng thành công!</div></html>", 
                "Thành công", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Có lỗi xảy ra khi thêm vào giỏ hàng", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBuyNow(Product product, TrangChu parentFrame, int quantity) {
        // Implement buy now functionality
        JOptionPane.showMessageDialog(this, 
            "Tính năng Mua ngay đang được phát triển", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}