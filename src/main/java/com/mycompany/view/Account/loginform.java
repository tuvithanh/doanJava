package com.mycompany.view.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mycompany.service.Admin.UserService;
import com.mycompany.model.User;
import com.mycompany.CredentialManager.CredentialManager;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.TrangChu.TrangChu;
import javax.swing.border.*;

public class loginform extends JFrame {
    private UserService userSer = new UserService();
    private JCheckBox chkRemember;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private Color primaryColor = new Color(0, 102, 204);
    private Color secondaryColor = new Color(240, 245, 255);
    private Color accentColor = new Color(0, 150, 136);

    public loginform() {
        initComponents();
        loadSavedCredentials();
    }

    private void initComponents() {
        setTitle("ĐĂNG NHẬP HỆ THỐNG");
        setSize(450, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), secondaryColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        add(mainPanel);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblLogo = new JLabel("HỆ THỐNG BÁN QUẦN ÁO");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLogo.setForeground(primaryColor);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubLogo = new JLabel("ĐĂNG NHẬP TÀI KHOẢN");
        lblSubLogo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubLogo.setForeground(new Color(100, 100, 100));
        lblSubLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubLogo.setBorder(new EmptyBorder(5, 0, 30, 0));

        headerPanel.add(lblLogo);
        headerPanel.add(lblSubLogo);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel lblUsername = new JLabel("Tên đăng nhập");
        lblUsername.setFont(labelFont);
        lblUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblUsername.setBorder(new EmptyBorder(5, 5, 5, 5));

        txtUsername = createStyledTextField();
        txtUsername.setFont(inputFont);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel lblPassword = new JLabel("Mật khẩu");
        lblPassword.setFont(labelFont);
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPassword.setBorder(new EmptyBorder(15, 5, 5, 5));

        txtPassword = createStyledPasswordField();
        txtPassword.setFont(inputFont);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        chkRemember = new JCheckBox("Ghi nhớ đăng nhập");
        chkRemember.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkRemember.setOpaque(false);
        chkRemember.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkRemember.setBorder(new EmptyBorder(10, 5, 20, 5));

        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBackground(accentColor);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(new EmptyBorder(12, 0, 12, 0));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(accentColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(accentColor);
            }
        });

        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        registerPanel.setOpaque(false);
        JLabel lblRegister1 = new JLabel("Chưa có tài khoản?");
        lblRegister1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JLabel lblRegister2 = new JLabel("Đăng ký ngay");
        lblRegister2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRegister2.setForeground(primaryColor);
        lblRegister2.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerPanel.add(lblRegister1);
        registerPanel.add(lblRegister2);
        registerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);
        formPanel.add(chkRemember);
        formPanel.add(btnLogin);
        formPanel.add(registerPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        JLabel lblFooter = new JLabel("© 2025 Hệ thống quản lý. Bản quyền thuộc nhóm 6.");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(120, 120, 120));
        footerPanel.add(lblFooter);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Register click
        lblRegister2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new registerform().setVisible(true);
            }
        });

        // Login button click
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());

            if (username.isEmpty() || pass.isEmpty()) {
                showErrorDialog("Vui lòng điền đầy đủ thông tin đăng nhập!");
                return;
            }

            User loginUser = userSer.getUserByUserName(username);
            if (loginUser == null || !loginUser.getPassword().equals(pass)) {
                showErrorDialog("Tên đăng nhập hoặc mật khẩu không chính xác!");
                return;
            }

            UserSession.currentUsername = username;

            if (chkRemember.isSelected()) {
                CredentialManager.saveLogin(username, pass);
            } else {
                CredentialManager.clearLogin();
            }

            showSuccessDialog("Đăng nhập thành công!");
            dispose();
            new TrangChu().setVisible(true);
        });
    }

    private void loadSavedCredentials() {
        String[] saved = CredentialManager.loadLogin();
        if (saved != null) {
            txtUsername.setText(saved[0]);
            txtPassword.setText(saved[1]);
            chkRemember.setSelected(true);
        }
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, Color.GRAY),
            new EmptyBorder(8, 8, 8, 8)
        ));
        tf.setOpaque(false);
        tf.setBackground(new Color(255, 255, 255, 150));
        return tf;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField pf = new JPasswordField();
        pf.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, Color.GRAY),
            new EmptyBorder(8, 8, 8, 8)
        ));
        pf.setOpaque(false);
        pf.setBackground(new Color(255, 255, 255, 150));
        return pf;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this,
            "<html><div style='text-align: center;'>" + message + "</div></html>",
            "Lỗi đăng nhập",
            JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this,
            "<html><div style='text-align: center;'>" + message + "</div></html>",
            "Thành công",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new loginform().setVisible(true);
        });
    }
}
