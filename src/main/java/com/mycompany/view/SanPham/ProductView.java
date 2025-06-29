package com.mycompany.view.SanPham;

import javax.swing.*;
import java.awt.*;
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
        initUI();
    }

    public void initUI() {
        removeAll(); // Xóa sản phẩm cũ

        setLayout(new GridLayout(0, 3, 20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

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
            try {
                ImageIcon icon = new ImageIcon(p.getImagePath());
                Image img = icon.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
                imgLabel = new JLabel(new ImageIcon(img));
            } catch (Exception e) {
                imgLabel = new JLabel("Không có ảnh");
                imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }

            JLabel name = new JLabel(p.getName());
            name.setFont(new Font("Arial", Font.BOLD, 16));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel price = new JLabel("Giá: " + p.getPrice() + " đ");
            price.setFont(new Font("Arial", Font.PLAIN, 14));
            price.setForeground(Color.RED);
            price.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel desc = new JLabel("<html><div style='text-align: center;'>" + p.getDescription() + "</div></html>");
            desc.setFont(new Font("Arial", Font.ITALIC, 12));
            desc.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton btnAddToCart = new JButton("Thêm vào giỏ hàng");
            btnAddToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnAddToCart.addActionListener(e -> addToCart(p));

            card.add(imgLabel);
            card.add(Box.createVerticalStrut(10));
            card.add(name);
            card.add(desc);
            card.add(Box.createVerticalStrut(5));
            card.add(price);
            card.add(Box.createVerticalStrut(10));
            card.add(btnAddToCart);

            add(card);
        }

        setBackground(new Color(245, 245, 245));
        revalidate();
        repaint();
    }

    private void addToCart(Product product) {
    if (UserSession.currentUsername == null || UserSession.currentUsername.trim().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng!");

    // Lấy JFrame chứa JPanel hiện tại
    Window window = SwingUtilities.getWindowAncestor(this);
    if (window instanceof JFrame) {
        ((JFrame) window).dispose(); // đóng JFrame hiện tại
    }

    // Mở form đăng nhập
    new loginform().setVisible(true);
    return;
}


    try {
        int userId = new UserService().getUserByUserName(UserSession.currentUsername).getId();
        CartService cartService = new CartService();
        Cart cart = cartService.getOrCreateCart(userId);

        // Kiểm tra sản phẩm đã có trong giỏ chưa
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
        initUI();
    }
}
