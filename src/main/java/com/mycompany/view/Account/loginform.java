package com.mycompany.view.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mycompany.service.Admin.UserService;
import com.mycompany.model.User;

public class loginform extends JFrame {
    UserService userSer = new UserService();
    public loginform() {
        setTitle("Đăng nhập");
        setSize(350, 240);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        btnLogin.addActionListener(e -> {
    String username = txtUsername.getText().trim();
    String pass = new String(txtPassword.getPassword());

    if (username.isEmpty() || pass.isEmpty()) {
        JOptionPane.showMessageDialog(
            loginform.this, // Thay "this" bằng loginform.this (nếu lớp hiện tại là loginform)
            "Vui lòng điền đầy đủ thông tin!",
            "Lỗi",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    // TODO: Thêm xử lý đăng nhập ở đây, ví dụ:
    
        User loginUser = userSer.getUserByUserName(username);
        if (loginUser == null || !loginUser.getPassword().equals(pass)) {
            JOptionPane.showMessageDialog(
                loginform.this,
                "Sai tên đăng nhập hoặc mật khẩu!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
            loginform.this,
            "Đăng nhập thành công!",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE
        );

        // TODO: Chuyển sang giao diện khác nếu cần
    });

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new loginform().setVisible(true);
//        });
//    }
}
