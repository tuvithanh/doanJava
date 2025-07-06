    package com.mycompany.view.SanPham;

    import javax.swing.*;
    import java.awt.*;
    import com.mycompany.dao.ProductDao;
    import com.mycompany.model.Product;
    import java.util.List;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

   public class ProductView extends JPanel {
    public static ProductView instance; // Biến static toàn cục

    public ProductView() {
        instance = this; // Gán khi khởi tạo
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

    card.add(imgLabel);
    card.add(Box.createVerticalStrut(10));
    card.add(name);
    card.add(desc);
    card.add(Box.createVerticalStrut(5));
    card.add(price);

    // 👇 Bổ sung sự kiện click để hiển thị DetailProduct
    card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    card.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ProductView.this);
            com.mycompany.view.ChiTietSanPham.DetailProduct detail = new com.mycompany.view.ChiTietSanPham.DetailProduct(parentFrame, p);
            detail.setVisible(true);
        }
    });

    add(card);
}


        setBackground(new Color(245, 245, 245));
        revalidate();
        repaint();
    }

    public void reloadData() {
        initUI();
    }
}

   
