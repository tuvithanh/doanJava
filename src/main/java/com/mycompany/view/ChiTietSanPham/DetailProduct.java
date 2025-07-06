package com.mycompany.view.ChiTietSanPham;

import javax.swing.*;
import java.awt.*;
import com.mycompany.model.Product;
import com.mycompany.view.SanPham.ProductView;
public class DetailProduct extends JDialog {
    public DetailProduct(JFrame parent, Product product) {
        super(parent, "Chi tiết sản phẩm", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel name = new JLabel(product.getName());
        name.setFont(new Font("Arial", Font.BOLD, 22));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel desc = new JLabel("<html><div style='text-align: center;'>" + product.getDescription() + "</div></html>");
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel price = new JLabel("Giá: " + product.getPrice() + " đ");
        price.setForeground(Color.RED);
        price.setFont(new Font("Arial", Font.PLAIN, 18));
        price.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imgLabel;
        try {
            ImageIcon icon = new ImageIcon(product.getImagePath());
            Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            imgLabel = new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            imgLabel = new JLabel("Không có ảnh");
        }
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton closeBtn = new JButton("Đóng");
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(e -> dispose());

        panel.add(imgLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(name);
        panel.add(desc);
        panel.add(Box.createVerticalStrut(10));
        panel.add(price);
        panel.add(Box.createVerticalStrut(20));
        panel.add(closeBtn);

        add(panel, BorderLayout.CENTER);
    }
}
