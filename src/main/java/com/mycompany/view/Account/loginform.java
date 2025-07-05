package com.mycompany.view.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mycompany.service.Admin.UserService;
import com.mycompany.model.User;
import com.mycompany.CredentialManager.CredentialManager;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.TrangChu.TrangChu;
import javax.swing.border.Border;

public class loginform extends JFrame {
    UserService userSer = new UserService();
    JCheckBox chkRemember;

    public loginform() {
        setTitle("Đăng nhập");
        setSize(380, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.decode("#f0f4f8")); // nền nhẹ

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setBounds(40, 40, 120, 25);
        lblUsername.setFont(fontLabel);
        add(lblUsername);

        JTextField txtUsername = createRoundedTextField();
        txtUsername.setBounds(160, 40, 170, 35);
        txtUsername.setFont(fontInput);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setBounds(40, 85, 120, 25);
        lblPassword.setFont(fontLabel);
        add(lblPassword);

        JPasswordField txtPassword = createRoundedPasswordField();
        txtPassword.setBounds(160, 85, 170, 35);
        txtPassword.setFont(fontInput);
        add(txtPassword);

        chkRemember = new JCheckBox("Ghi nhớ đăng nhập");
        chkRemember.setBounds(160, 125, 170, 25);
        chkRemember.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkRemember.setBackground(new Color(0, 0, 0, 0));
        add(chkRemember);

        JLabel lblRegister = new JLabel("<HTML><U>Đăng ký</U></HTML>");
        lblRegister.setForeground(new Color(0, 102, 204));
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.setBounds(90, 170, 60, 25);
        lblRegister.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(lblRegister);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(160, 165, 130, 35);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(33, 150, 243));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(25, 118, 210));
            }

            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(33, 150, 243));
            }
        });

        add(btnLogin);

        // Đọc lại nếu có
        String[] saved = CredentialManager.loadLogin();
        if (saved != null) {
            txtUsername.setText(saved[0]);
            txtPassword.setText(saved[1]);
            chkRemember.setSelected(true);
        }

        lblRegister.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new registerform().setVisible(true);
            }
        });

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());

            if (username.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User loginUser = userSer.getUserByUserName(username);
            if (loginUser == null || !loginUser.getPassword().equals(pass)) {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserSession.currentUsername = username;

            if (chkRemember.isSelected()) {
                CredentialManager.saveLogin(username, pass);
            } else {
                CredentialManager.clearLogin();
            }

            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new TrangChu().setVisible(true);
        });
    }

    private JTextField createRoundedTextField() {
        JTextField tf = new JTextField() {
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        tf.setOpaque(false);
        tf.setBorder(new RoundedBorder(10));
        return tf;
    }

    private JPasswordField createRoundedPasswordField() {
        JPasswordField pf = new JPasswordField() {
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        pf.setOpaque(false);
        pf.setBorder(new RoundedBorder(10));
        return pf;
    }

    // Class để vẽ border bo góc
    private static class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
