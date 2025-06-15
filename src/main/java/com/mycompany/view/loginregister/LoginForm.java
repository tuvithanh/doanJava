package com.mycompany.view.loginregister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;
    private JLabel registerLabel;

    public static Map<String, String> accountDB = new HashMap<>();

    public LoginForm() {
        setTitle("Đăng nhập hệ thống");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        accountDB.put("admin", "123");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        registerLabel = new JLabel("<html><u>Chưa có tài khoản? Đăng ký tại đây</u></html>");
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Đăng nhập");
        cancelButton = new JButton("Thoát");
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(Box.createVerticalStrut(10));

        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(10));

        mainPanel.add(registerLabel);
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(buttonPanel);

        add(mainPanel);

        loginButton.addActionListener(e -> login());
        cancelButton.addActionListener(e -> System.exit(0));
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                openRegisterForm();
            }
        });
    }

    private void login() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (accountDB.containsKey(username) && accountDB.get(username).equals(password)) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegisterForm() {
        RegisterForm registerForm = new RegisterForm(this);
        registerForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
