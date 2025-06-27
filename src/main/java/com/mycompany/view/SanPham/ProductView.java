    package com.mycompany.view.SanPham;

    import javax.swing.*;
    import java.awt.*;
    import com.mycompany.dao.ProductDao;
    import com.mycompany.model.Product;
    import java.util.List;

    public class ProductView extends JPanel {
        public ProductView() {
            setLayout(new GridLayout(0, 2, 10, 10)); // 2 cột
            ProductDao dao = new ProductDao();
            List<Product> list = dao.getAllProducts();

            for (Product p : list) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel name = new JLabel(p.getName(), SwingConstants.CENTER);
                JLabel price = new JLabel("Giá: " + p.getPrice() + "đ", SwingConstants.CENTER);
                JLabel desc = new JLabel(p.getDescription(), SwingConstants.CENTER);
               JLabel img = new JLabel("Hình: " + p.getImagePath()); // Tạm, có thể dùng ImageIcon sau

                card.add(name, BorderLayout.NORTH);
                card.add(desc, BorderLayout.CENTER);
                card.add(price, BorderLayout.SOUTH);

                add(card);
            }
        }
        
    }
