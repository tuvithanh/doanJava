package com.mycompany.view.Favorite;

import com.mycompany.dao.FavoriteDao;
import com.mycompany.dao.ProductDao;
import com.mycompany.model.CartItem;
import com.mycompany.model.Favorite;
import com.mycompany.model.Product;
import com.mycompany.model.Cart;
import com.mycompany.model.User;
import com.mycompany.service.Admin.CartService;
import com.mycompany.service.Admin.FavoriteService;
import com.mycompany.service.Admin.UserService;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.Account.loginform;
import com.mycompany.view.ChiTietSanPham.DetailProduct;
import com.mycompany.view.TrangChu.TrangChu;
import com.mycompany.view.SanPham.ProductView;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class FavoriteView extends JPanel {
    private JPanel contentPanel;
    private Color primaryColor = new Color(0, 123, 255);
    private Color secondaryColor = new Color(245, 245, 245);
    private Color textColor = new Color(60, 60, 60);
    
    public FavoriteView() {
        setLayout(new BorderLayout());
        setBackground(secondaryColor);

        // === TOP PANEL ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        topPanel.setBackground(Color.WHITE);

        // === TITLE PANEL ===
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("SẢN PHẨM YÊU THÍCH");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(primaryColor);
        titlePanel.add(titleLabel);

        // === ADD COMPONENTS TO TOP PANEL ===
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(titlePanel, BorderLayout.WEST);

        topPanel.add(leftPanel, BorderLayout.WEST);

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

        loadFavorites();
    }

    private void loadFavorites() {
        contentPanel.removeAll();
        
        if (UserSession.currentUsername == null) {
            JLabel emptyLabel = new JLabel("Vui lòng đăng nhập để xem sản phẩm yêu thích", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setForeground(new Color(150, 150, 150));
            contentPanel.add(emptyLabel);
            contentPanel.revalidate();
            contentPanel.repaint();
            return;
        }

        User user = new UserService().getUserByUserName(UserSession.currentUsername);
        FavoriteDao favDao = new FavoriteDao();
        ProductDao proDao = new ProductDao();

        List<Favorite> favList = favDao.getFavoritesByUserId(user.getId());

        if (favList.isEmpty()) {
            JLabel emptyLabel = new JLabel("Bạn chưa yêu thích sản phẩm nào", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setForeground(new Color(150, 150, 150));
            contentPanel.add(emptyLabel);
            contentPanel.revalidate();
            contentPanel.repaint();
            return;
        }

        for (Favorite fav : favList) {
            Product p = proDao.getProductByID(fav.getProductId());
            if (p == null) continue;

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
            ImageIcon heartFilled = new ImageIcon("src/main/java/images/heart_filled.png");
            JLabel heartLabel = new JLabel(heartFilled);
            heartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            heartLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        FavoriteService favService = new FavoriteService();
                        boolean isNowFavorite = favService.toggleFavorite(user.getId(), p.getId());

                        if (!isNowFavorite) {
                            // Nếu vừa bỏ yêu thích thì ẩn thẻ
                            card.setVisible(false);
                            contentPanel.remove(card);
                            contentPanel.revalidate();
                            contentPanel.repaint();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(FavoriteView.this,
                            "Lỗi khi xử lý yêu thích: " + ex.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    e.consume();
                }
            });


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
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(FavoriteView.this);
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
}