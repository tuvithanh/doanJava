package com.mycompany.view.loginregister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton, cancelButton;
    private LoginForm loginForm;

    public RegisterForm(LoginForm loginForm) {
        this.loginForm = loginForm;

        setTitle("Đăng ký tài khoản");
        setSize(350, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        JLabel confirmPasswordLabel = new JLabel("Nhập lại mật khẩu:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        registerButton = new JButton("Đăng ký");
        cancelButton = new JButton("Hủy");

        add(usernameLabel); add(usernameField);
        add(passwordLabel); add(passwordField);
        add(confirmPasswordLabel); add(confirmPasswordField);
        add(registerButton); add(cancelButton);

        registerButton.addActionListener(e -> register());
        cancelButton.addActionListener(e -> dispose());
    }

    private void register() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());
        String confirm = String.valueOf(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không trùng khớp.");
            return;
        }

        if (LoginForm.accountDB.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại.");
        } else {
            LoginForm.accountDB.put(username, password);
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            dispose();
        }
    }
}
