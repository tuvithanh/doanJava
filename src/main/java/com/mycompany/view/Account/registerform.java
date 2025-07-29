package com.mycompany.view.Account;

import com.mycompany.model.User;
import com.mycompany.service.Admin.UserService;
import com.mycompany.util.EmailSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registerform extends JFrame {
    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword, txtRePassword;
    private JButton btnRegister, btnCancel;
    private UserService userSer = new UserService();

    public registerform() {
        setTitle("Đăng ký tài khoản");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        // Username
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Tên đăng nhập:"), gbc);

        txtUsername = new JTextField(20);
        txtUsername.setFont(inputFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(txtUsername, gbc);

        // Password
        gbc.gridy = 2;
        add(new JLabel("Mật khẩu:"), gbc);

        txtPassword = new JPasswordField(20);
        txtPassword.setFont(inputFont);
        gbc.gridy = 3;
        add(txtPassword, gbc);

        // Re-password
        gbc.gridy = 4;
        add(new JLabel("Xác nhận mật khẩu:"), gbc);

        txtRePassword = new JPasswordField(20);
        txtRePassword.setFont(inputFont);
        gbc.gridy = 5;
        add(txtRePassword, gbc);

        // Email
        gbc.gridy = 6;
        add(new JLabel("Email:"), gbc);

        txtEmail = new JTextField(20);
        txtEmail.setFont(inputFont);
        gbc.gridy = 7;
        add(txtEmail, gbc);

        // Buttons
        JPanel panelButtons = new JPanel();
        btnRegister = new JButton("Đăng ký");
        btnCancel = new JButton("Hủy");
        panelButtons.add(btnRegister);
        panelButtons.add(btnCancel);

        gbc.gridy = 8;
        add(panelButtons, gbc);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });

        btnCancel.addActionListener(e -> {
            dispose();
            new loginform().setVisible(true);
        });
    }

    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());
        String rePass = new String(txtRePassword.getPassword());
        String email = txtEmail.getText().trim();

        if (username.isEmpty() || pass.isEmpty() || rePass.isEmpty() || email.isEmpty()) {
            showErrorDialog("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        if (!pass.equals(rePass)) {
            showErrorDialog("Mật khẩu không khớp!");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            showErrorDialog("Email không hợp lệ!");
            return;
        }

        if (userSer.getUserByUserName(username) != null) {
            showErrorDialog("Tên tài khoản đã tồn tại!");
            return;
        }

        String code = generateVerificationCode();
        boolean sent = EmailSender.sendVerificationCode(email, code);
        if (!sent) {
            showErrorDialog("Không thể gửi mã xác nhận đến email.");
            return;
        }

        String inputCode = JOptionPane.showInputDialog(this, "Nhập mã xác nhận đã gửi đến email:");
        if (inputCode == null || !inputCode.equals(code)) {
            showErrorDialog("Mã xác nhận không đúng!");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(pass);
        user.setEmail(email);
        user.setRole('C');

        try {
            userSer.insert(user);
            showSuccessDialog("Đăng ký thành công!");
            dispose();
            new loginform().setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorDialog("Đăng ký thất bại!");
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    private String generateVerificationCode() {
        int code = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }
}
