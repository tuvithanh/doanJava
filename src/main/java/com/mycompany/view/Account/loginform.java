package com.mycompany.view.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class loginform extends JFrame {
    public loginform() {
        setTitle("Đăng nhập");
        setSize(350, 240);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setBounds(30, 30, 100, 25);
        add(lblUsername);

        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(140, 30, 150, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setBounds(30, 70, 100, 25);
        add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(140, 70, 150, 25);
        add(txtPassword);

        // Dòng chữ "Đăng ký" nằm ngay dưới ô mật khẩu
        JLabel lblRegister = new JLabel("<HTML><U>Đăng ký</U></HTML>");
        lblRegister.setForeground(Color.BLUE);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.setBounds(140, 100, 100, 25);
        add(lblRegister);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(110, 140, 120, 30);
        add(btnLogin);

        // Sự kiện nhấn "Đăng ký" mở form đăng ký
        lblRegister.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose(); // Ẩn form đăng nhập hiện tại
                new registerform().setVisible(true); // Mở form đăng ký
            }
        });
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new loginform().setVisible(true);
//        });
//    }
}
