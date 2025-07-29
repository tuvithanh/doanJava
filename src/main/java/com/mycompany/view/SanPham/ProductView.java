package com.mycompany.view.SanPham;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.border.*;
import com.mycompany.dao.ProductDao;
import com.mycompany.model.*;
import com.mycompany.service.Admin.*;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.Account.loginform;
import com.mycompany.view.ChiTietSanPham.DetailProduct;
import com.mycompany.view.TrangChu.TrangChu;
import java.awt.event.*;
import com.mycompany.dao.CategoryDao;
import com.mycompany.service.Admin.FavoriteService;

public class ProductView extends JPanel {
    public static ProductView instance;
    private JPanel contentPanel;
    private Color primaryColor = new Color(0, 123, 255);
    private Color secondaryColor = new Color(245, 245, 245);
    private Color textColor = new Color(60, 60, 60);
    private JTextField searchField;
    
    public ProductView() {
        instance = this;
        setLayout(new BorderLayout());
        setBackground(secondaryColor);

        // === TOP PANEL ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        topPanel.setBackground(Color.WHITE);

        // === TITLE PANEL ===
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("SẢN PHẨM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(primaryColor);
        titlePanel.add(titleLabel);

        // === SEARCH PANEL ===
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(new EmptyBorder(0, 0, 0, 20));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1), // viền ngoài
            new EmptyBorder(5, 10, 5, 10)                // khoảng cách bên trong
        ));


        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchButton.setBackground(primaryColor);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(new EmptyBorder(5, 15, 5, 15));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(e -> searchProducts());

        // Thêm sự kiện khi nhấn Enter trong searchField
        searchField.addActionListener(e -> searchProducts());

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // === FILTER PANEL ===
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setOpaque(false);

        JButton btnChonDanhMuc = new JButton("LỌC THEO DANH MỤC");
        btnChonDanhMuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnChonDanhMuc.setBackground(Color.WHITE);
        btnChonDanhMuc.setForeground(textColor);
        btnChonDanhMuc.setFocusPainted(false);
        btnChonDanhMuc.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(5, 15, 5, 15)
        ));
        btnChonDanhMuc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        filterPanel.add(btnChonDanhMuc);

        // === POPUP MENU DANH MỤC ===
        JPopupMenu popupDanhMuc = new JPopupMenu();
        popupDanhMuc.setBorder(new LineBorder(new Color(230, 230, 230)));
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getAllCategory();

        JMenuItem allItem = new JMenuItem("TẤT CẢ SẢN PHẨM");
        allItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        allItem.addActionListener(e -> loadProductList(contentPanel, -1, ""));
        popupDanhMuc.add(allItem);
        popupDanhMuc.addSeparator();

        for (Category cat : categoryList) {
            JMenuItem item = new JMenuItem(cat.getName());
            item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            item.addActionListener(e -> loadProductList(contentPanel, cat.getId(), ""));
            popupDanhMuc.add(item);
        }

        btnChonDanhMuc.addActionListener(e ->
                popupDanhMuc.show(btnChonDanhMuc, 0, btnChonDanhMuc.getHeight()));

        // === ADD COMPONENTS TO TOP PANEL ===
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(titlePanel, BorderLayout.WEST);
        leftPanel.add(filterPanel, BorderLayout.CENTER);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // === CONTENT PANEL ===
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(null);
        scrollPane.setBackground(secondaryColor);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 4, 25, 25));
        contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        contentPanel.setBackground(secondaryColor);

        scrollPane.setViewportView(contentPanel);
        add(scrollPane, BorderLayout.CENTER);

        loadProductList(contentPanel, -1, "");
    }

    private void searchProducts() {
        String keyword = searchField.getText().trim();
        loadProductList(contentPanel, -1, keyword);
    }
    public void searchProducts(String keyword) {
        searchField.setText(keyword); // cập nhật ô nhập để đồng bộ giao diện
        loadProductList(contentPanel, -1, keyword);
    }



    private void loadProductList(JPanel contentPanel, int cateId, String keyword) {
        contentPanel.removeAll();

        ProductDao dao = new ProductDao();
        List<Product> list = dao.getAllProducts();

        // Lọc theo danh mục
        if (cateId != -1) {
            list.removeIf(p -> p.getCateid() != cateId);
        }

        // Lọc theo từ khóa tìm kiếm
        if (!keyword.isEmpty()) {
            String searchLower = keyword.toLowerCase();
            list.removeIf(p -> !p.getName().toLowerCase().contains(searchLower) && 
                             !p.getDescription().toLowerCase().contains(searchLower));
        }

        for (Product p : list) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(new CompoundBorder(
                    new LineBorder(new Color(230, 230, 230), 1),
                    new EmptyBorder(15, 15, 15, 15)
            ));
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // === ẢNH SẢN PHẨM ===
            JLabel imgLabel;
            String imagePath = p.getImagepath();
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                Image img = icon.getImage();
                int width = img.getWidth(null);
                int height = img.getHeight(null);
                if (width > 0 && height > 0) {
                    img = img.getScaledInstance(180, 150, Image.SCALE_SMOOTH);
                    imgLabel = new JLabel(new ImageIcon(img));
                } else {
                    imgLabel = new JLabel("Ảnh lỗi", SwingConstants.CENTER);
                }
            } else {
                imgLabel = new JLabel("Không có ảnh", SwingConstants.CENTER);
                imgLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                imgLabel.setForeground(new Color(150, 150, 150));
            }

            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imgLabel.setVerticalAlignment(SwingConstants.CENTER);
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // === TRÁI TIM ===
            ImageIcon heartEmpty = new ImageIcon("src/main/java/images/heart_empty.png");
            ImageIcon heartFilled = new ImageIcon("src/main/java/images/heart_filled.png");
            JLabel heartLabel = new JLabel(heartEmpty);
            heartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            if (UserSession.currentUsername != null && !UserSession.currentUsername.isEmpty()) {
                try {
                    int userId = new UserService().getUserByUserName(UserSession.currentUsername).getId();
                    boolean isFavorited = new FavoriteService().isFavorite(userId, p.getId());
                    heartLabel.setIcon(isFavorited ? heartFilled : heartEmpty);

                    heartLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                boolean nowFavorite = new FavoriteService().toggleFavorite(userId, p.getId());
                                heartLabel.setIcon(nowFavorite ? heartFilled : heartEmpty);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(ProductView.this,
                                    "Lỗi khi xử lý yêu thích: " + ex.getMessage(),
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                            e.consume();
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                heartLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int option = JOptionPane.showConfirmDialog(ProductView.this,
                            "Bạn cần đăng nhập để sử dụng chức năng yêu thích.\nBạn có muốn đăng nhập ngay bây giờ?",
                            "Yêu cầu đăng nhập", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            Window window = SwingUtilities.getWindowAncestor(ProductView.this);
                            if (window instanceof JFrame) {
                                ((JFrame) window).dispose();
                            }
                            new loginform().setVisible(true);
                        }
                    }
                });
            }

            JPanel imageContainer = new JPanel(new BorderLayout());
            imageContainer.setBackground(Color.WHITE);
            imageContainer.add(imgLabel, BorderLayout.CENTER);
            JPanel heartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            heartPanel.setBackground(new Color(255, 255, 255, 150));
            heartPanel.add(heartLabel);
            imageContainer.add(heartPanel, BorderLayout.NORTH);

            JLabel name = new JLabel(p.getName());
            name.setFont(new Font("Segoe UI", Font.BOLD, 15));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            name.setForeground(textColor);
            name.setMaximumSize(new Dimension(180, 40));

            JLabel price = new JLabel(String.format("%,.0f đ", p.getPrice()));
            price.setFont(new Font("Segoe UI", Font.BOLD, 14));
            price.setForeground(new Color(220, 0, 0));
            price.setAlignmentX(Component.CENTER_ALIGNMENT);

            JTextArea desc = new JTextArea(p.getDescription());
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            desc.setEditable(false);
            desc.setOpaque(false);
            desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            desc.setForeground(new Color(120, 120, 120));
            desc.setAlignmentX(Component.CENTER_ALIGNMENT);
            desc.setMaximumSize(new Dimension(180, 40));
            desc.setBorder(new EmptyBorder(5, 0, 5, 0));

            JButton btnAddToCart = new JButton("THÊM VÀO GIỎ");
            btnAddToCart.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnAddToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnAddToCart.setBackground(primaryColor);
            btnAddToCart.setForeground(Color.WHITE);
            btnAddToCart.setFocusPainted(false);
            btnAddToCart.setBorder(new EmptyBorder(8, 0, 8, 0));
            btnAddToCart.setMaximumSize(new Dimension(180, 30));
            btnAddToCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnAddToCart.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btnAddToCart.setBackground(new Color(0, 105, 217));
                }
                public void mouseExited(MouseEvent e) {
                    btnAddToCart.setBackground(primaryColor);
                }
            });
            btnAddToCart.addActionListener(e -> addToCart(p));

            card.add(imageContainer);
            card.add(Box.createVerticalStrut(10));
            card.add(name);
            card.add(Box.createVerticalStrut(5));
            card.add(price);
            card.add(Box.createVerticalStrut(5));
            card.add(desc);
            card.add(Box.createVerticalStrut(10));
            card.add(btnAddToCart);

            card.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ProductView.this);
                    if (parentFrame instanceof TrangChu trangChu) {
                        JPanel mainContent = trangChu.getContentPanel();
                        mainContent.removeAll();
                        mainContent.add(new DetailProduct(trangChu, p));
                        mainContent.revalidate();
                        mainContent.repaint();
                    }
                }
            });

            contentPanel.add(card);
        }

        if (list.isEmpty()) {
            JLabel emptyLabel = new JLabel("Không tìm thấy sản phẩm nào phù hợp", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setForeground(new Color(150, 150, 150));
            contentPanel.add(emptyLabel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addToCart(Product product) {
        if (UserSession.currentUsername == null || UserSession.currentUsername.trim().isEmpty()) {
            int option = JOptionPane.showConfirmDialog(this,
                "Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng.\nBạn có muốn đăng nhập ngay bây giờ?",
                "Yêu cầu đăng nhập", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window instanceof JFrame) {
                    ((JFrame) window).dispose();
                }
                new loginform().setVisible(true);
            }
            return;
        }

        try {
            int userId = new UserService().getUserByUserName(UserSession.currentUsername).getId();
            CartService cartService = new CartService();
            Cart cart = cartService.getOrCreateCart(userId);

            List<CartItem> existingItems = cartService.getItems(cart.getId());
            CartItem matchedItem = existingItems.stream()
                .filter(i -> i.getProductId() == product.getId())
                .findFirst().orElse(null);

            if (matchedItem != null) {
                cartService.updateItemQuantity(matchedItem.getId(), matchedItem.getQuantity() + 1);
            } else {
                CartItem newItem = new CartItem();
                newItem.setCartId(cart.getId());
                newItem.setProductId(product.getId());
                newItem.setQuantity(1);
                cartService.addItem(newItem);
            }

            JOptionPane.showMessageDialog(this,
                "Đã thêm '" + product.getName() + "' vào giỏ hàng!",
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi thêm vào giỏ hàng: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void reloadData() {
        loadProductList(contentPanel, -1, "");
    }
}