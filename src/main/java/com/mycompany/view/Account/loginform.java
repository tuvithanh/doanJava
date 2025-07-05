package com.mycompany.view.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mycompany.service.Admin.UserService;
import com.mycompany.model.User;
import com.mycompany.CredentialManager.CredentialManager;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.TrangChu.TrangChu; // Nhớ import

public class loginform extends JFrame {
    UserService userSer = new UserService();
    JCheckBox chkRemember;

    public loginform() {
        setLocationRelativeTo(null);
        setTitle("Đăng nhập");
        setSize(350, 240);
        getContentPane().setBackground( Color.decode("#7FA1C3") );
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

        JLabel lblRegister = new JLabel("<HTML><U>Đăng ký</U></HTML>");
        lblRegister.setForeground(Color.BLUE);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.setBounds(80, 100, 50, 25);
        add(lblRegister);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(110, 140, 120, 30);
        add(btnLogin);

        chkRemember = new JCheckBox("Remember me");
        chkRemember.setBounds(140, 100, 150, 25);
        add(chkRemember);

        lblRegister.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new registerform().setVisible(true);
            }
        });

        // ✅ Đọc lại thông tin đã lưu nếu có
        String[] saved = CredentialManager.loadLogin();
        if (saved != null) {
            txtUsername.setText(saved[0]);
            txtPassword.setText(saved[1]);
            chkRemember.setSelected(true);
        }

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());

            if (username.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(
                    loginform.this,
                    "Vui lòng điền đầy đủ thông tin!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

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

            // ✅ Ghi nhớ username hiện tại
            UserSession.currentUsername = username;

            // ✅ Ghi login nếu chọn
            if (chkRemember.isSelected()) {
                CredentialManager.saveLogin(username, pass);
            } else {
                CredentialManager.clearLogin();
            }

            JOptionPane.showMessageDialog(
                loginform.this,
                "Đăng nhập thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
            );

            // ✅ Mở TrangChu và đóng login
            dispose();
            new TrangChu().setVisible(true);
        });
    }
}
