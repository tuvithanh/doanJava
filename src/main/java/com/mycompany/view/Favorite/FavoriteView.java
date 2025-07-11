/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.view.Favorite;

/**
 *
 * @author VITHANH
 */


import com.mycompany.dao.FavoriteDao;
import com.mycompany.dao.ProductDao;
import com.mycompany.model.Favorite;
import com.mycompany.model.Product;
import com.mycompany.model.User;
import com.mycompany.service.Admin.UserService;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.ChiTietSanPham.DetailProduct;
import com.mycompany.view.TrangChu.TrangChu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class FavoriteView extends JPanel {

    public FavoriteView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("SẢN PHẨM YÊU THÍCH", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(0, 4, 20, 20));
        contentPanel.setBackground(new Color(245, 245, 245));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadFavorites(contentPanel);
    }

    private void loadFavorites(JPanel contentPanel) {
        contentPanel.removeAll();
        if (UserSession.currentUsername == null) {
            JLabel label = new JLabel("Bạn cần đăng nhập để xem danh sách yêu thích.", SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            contentPanel.add(label);
            return;
        }

        User user = new UserService().getUserByUserName(UserSession.currentUsername);
        FavoriteDao favDao = new FavoriteDao();
        ProductDao proDao = new ProductDao();

        List<Favorite> favList = favDao.getFavoritesByUserId(user.getId());

        if (favList.isEmpty()) {
            JLabel label = new JLabel("Bạn chưa yêu thích sản phẩm nào.", SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            contentPanel.add(label);
        }

        for (Favorite fav : favList) {
            Product p = proDao.getProductByID(fav.getProductId());
            if (p == null) continue;

            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220, 220, 220)),
                    new EmptyBorder(10, 10, 10, 10)));
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel imgLabel;
            File imgFile = new File(p.getImagepath());
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(160, 130, Image.SCALE_SMOOTH);
                imgLabel = new JLabel(new ImageIcon(img));
            } else {
                imgLabel = new JLabel("Không có ảnh", SwingConstants.CENTER);
                imgLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                imgLabel.setForeground(Color.GRAY);
            }
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel name = new JLabel(p.getName());
            name.setFont(new Font("Segoe UI", Font.BOLD, 14));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            name.setMaximumSize(new Dimension(160, 40));

            JLabel price = new JLabel(String.format("%,.0f đ", p.getPrice()));
            price.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            price.setForeground(new Color(220, 0, 0));
            price.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(imgLabel);
            card.add(Box.createVerticalStrut(10));
            card.add(name);
            card.add(Box.createVerticalStrut(5));
            card.add(price);

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
}

