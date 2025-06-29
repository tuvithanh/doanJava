package com.mycompany.view.Thongtintaikhoan;

import com.mycompany.model.User;
import com.mycompany.service.Admin.UserService;
import com.mycompany.sesion.UserSession.UserSession;

import javax.swing.*;
import java.awt.*;

public class Thongtintaikhoan extends JPanel {
    private JLabel lblTitle, lblUsername, lblName, lblPhone, lblEmail, lblAddress, lblAbout, lblRole;

    public Thongtintaikhoan() {
        setLayout(new GridBagLayout()); // Center toàn bộ panel
        setBackground(Color.WHITE);     // Nền trắng

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 245));
        contentPanel.setPreferredSize(new Dimension(400, 400));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblTitle = new JLabel("Thông tin tài khoản");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(33, 33, 33));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        contentPanel.add(lblTitle);

        lblUsername = createLabel();
        lblName = createLabel();
        lblPhone = createLabel();
        lblEmail = createLabel();
        lblAddress = createLabel();
        lblAbout = createLabel();
        lblRole = createLabel();

        contentPanel.add(lblUsername);
        contentPanel.add(lblName);
        contentPanel.add(lblPhone);
        contentPanel.add(lblEmail);
        contentPanel.add(lblAddress);
        contentPanel.add(lblAbout);
        contentPanel.add(lblRole);

        loadUserInfo();

        add(contentPanel); // Thêm vào chính giữa
    }

    private JLabel createLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    private void loadUserInfo() {
        String username = UserSession.currentUsername;
        if (username == null) {
            lblUsername.setText("️Bạn chưa đăng nhập.");
            return;
        }

        UserService userService = new UserService();
        User user = userService.getUserByUserName(username);

        if (user != null) {
            lblUsername.setText("Tài khoản: " + user.getUsername());
            lblName.setText("Họ tên: " + user.getName());
            lblPhone.setText("SĐT: " + user.getPhone());
            lblEmail.setText("Email: " + user.getEmail());
            lblAddress.setText("Địa chỉ: " + user.getAddress());
            lblAbout.setText("Giới thiệu: " + (user.getAbout() != null ? user.getAbout() : "(Không có)"));
        } else {
            lblUsername.setText("Không tìm thấy người dùng.");
        }
    }
}
