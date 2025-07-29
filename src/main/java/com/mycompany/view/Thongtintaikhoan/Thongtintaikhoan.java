package com.mycompany.view.Thongtintaikhoan;

import com.mycompany.model.User;
import com.mycompany.service.Admin.UserService;
import com.mycompany.sesion.UserSession.UserSession;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

public class Thongtintaikhoan extends JPanel {
    private JLabel lblTitle, lblUsername, lblName, lblPhone, lblEmail, lblAddress, lblAbout;
    private JButton btnEditInfo, btnChangePassword;
    private Color primaryColor = new Color(70, 130, 180); // Màu SteelBlue hiện đại
    private Color secondaryColor = new Color(248, 249, 250); // Màu nền nhẹ nhàng
    private Color accentColor = new Color(220, 53, 69); // Màu nhấn cho button thứ 2

    public Thongtintaikhoan() {
        setLayout(new BorderLayout());
        setBackground(secondaryColor);

        // Header panel với gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(70, 130, 180),
                        0, h, new Color(176, 224, 230));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        lblTitle = new JLabel("THÔNG TIN TÀI KHOẢN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.setLayout(new GridBagLayout());
        headerPanel.add(lblTitle);
        
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel với shadow effect
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2d.dispose();
            }
        };
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(0,0,0,0)); // Transparent
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        contentPanel.setOpaque(false);

        // User info panel với card effect
        JPanel infoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2d.setColor(new Color(230, 230, 230));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2d.dispose();
            }
        };
        infoPanel.setLayout(new GridLayout(0, 1, 10, 10));
        infoPanel.setBackground(new Color(0,0,0,0));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        infoPanel.setOpaque(false);

        lblUsername = createInfoLabel("Tài khoản: ");
        lblName = createInfoLabel("Họ tên: ");
        lblPhone = createInfoLabel("Số điện thoại: ");
        lblEmail = createInfoLabel("Email: ");
        lblAddress = createInfoLabel("Địa chỉ: ");
        lblAbout = createInfoLabel("Giới thiệu: ");

        infoPanel.add(lblUsername);
        infoPanel.add(lblName);
        infoPanel.add(lblPhone);
        infoPanel.add(lblEmail);
        infoPanel.add(lblAddress);
        infoPanel.add(lblAbout);

        contentPanel.add(infoPanel);
        contentPanel.add(Box.createVerticalStrut(30));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(new Color(0,0,0,0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        buttonPanel.setOpaque(false);

        btnEditInfo = createStyledButton("CHỈNH SỬA THÔNG TIN", "src/main/java/images/edit.png", primaryColor);
        btnEditInfo.addActionListener(e -> showEditInfoDialog());
        buttonPanel.add(btnEditInfo);

        btnChangePassword = createStyledButton("ĐỔI MẬT KHẨU", "src/main/java/images/lock.png", accentColor);
        btnChangePassword.addActionListener(e -> showChangePasswordDialog());
        buttonPanel.add(btnChangePassword);

        contentPanel.add(buttonPanel);

        // Main wrapper panel để tạo khoảng cách và shadow
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        wrapperPanel.setBackground(secondaryColor);
        wrapperPanel.add(contentPanel, BorderLayout.CENTER);

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(secondaryColor);
        add(scrollPane, BorderLayout.CENTER);

        loadUserInfo();
    }

    private JLabel createInfoLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        label.setForeground(new Color(70, 70, 70));
        
        // Tạo hiệu ứng card cho mỗi dòng thông tin
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        return label;
    }

    private JButton createStyledButton(String text, String iconPath, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        
        // Set icon if path is provided
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(iconPath);
            if (icon.getImage() != null) {
                Image scaledIcon = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledIcon));
                button.setIconTextGap(10);
            }
        }
        
        // Hiệu ứng hover và press
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(darker(bgColor, 0.8));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(darker(bgColor, 0.7));
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(darker(bgColor, 0.8));
            }
        });
        
        return button;
    }

    private Color darker(Color color, double factor) {
        return new Color(
            Math.max((int)(color.getRed() * factor), 0),
            Math.max((int)(color.getGreen() * factor), 0),
            Math.max((int)(color.getBlue() * factor), 0)
        );
    }

    private void loadUserInfo() {
        String username = UserSession.currentUsername;
        if (username == null) {
            lblUsername.setText("Tài khoản: Bạn chưa đăng nhập");
            return;
        }

        UserService userService = new UserService();
        User user = userService.getUserByUserName(username);

        if (user != null) {
            lblUsername.setText("Tài khoản: " + user.getUsername());
            lblName.setText("Họ tên: " + (user.getName() != null ? user.getName() : "Chưa cập nhật"));
            lblPhone.setText("Số điện thoại: " + (user.getPhone() != null ? user.getPhone() : "Chưa cập nhật"));
            lblEmail.setText("Email: " + (user.getEmail() != null ? user.getEmail() : "Chưa cập nhật"));
            lblAddress.setText("Địa chỉ: " + (user.getAddress() != null ? user.getAddress() : "Chưa cập nhật"));
            lblAbout.setText("Giới thiệu: " + (user.getAbout() != null ? user.getAbout() : "Chưa cập nhật"));
        } else {
            lblUsername.setText("Tài khoản: Không tìm thấy thông tin");
        }
    }

    private void showEditInfoDialog() {
        UserService userService = new UserService();
        User user = userService.getUserByUserName(UserSession.currentUsername);

        // Create dialog panel với thiết kế material
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 15, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add fields với material design
        JLabel[] labels = {
            new JLabel("Họ tên:"),
            new JLabel("Số điện thoại:"),
            new JLabel("Email:"),
            new JLabel("Địa chỉ:"),
            new JLabel("Giới thiệu:")
        };

        JTextField[] fields = {
            new JTextField(user.getName(), 20),
            new JTextField(user.getPhone(), 20),
            new JTextField(user.getEmail(), 20),
            new JTextField(user.getAddress(), 20),
            new JTextField(user.getAbout(), 20)
        };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            labels[i].setForeground(new Color(100, 100, 100));
            gbc.gridy = i * 2;
            panel.add(labels[i], gbc);
            
            fields[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 0, 5, 0)
            ));
            gbc.gridy = i * 2 + 1;
            panel.add(fields[i], gbc);
        }

        // Custom option buttons
        JButton btnSave = new JButton("Lưu thay đổi");
        btnSave.setBackground(primaryColor);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        JButton btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(220, 220, 220));
        btnCancel.setForeground(new Color(100, 100, 100));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);
        
        gbc.gridy = labels.length * 2 + 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttonPanel, gbc);

        // Create custom dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Chỉnh sửa thông tin");
        dialog.setModal(true);
        dialog.setContentPane(panel);
        dialog.getRootPane().setDefaultButton(btnSave);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        // Add action listeners
        btnSave.addActionListener(e -> {
            user.setName(fields[0].getText());
            user.setPhone(fields[1].getText());
            user.setEmail(fields[2].getText());
            user.setAddress(fields[3].getText());
            user.setAbout(fields[4].getText());

            String thongbao = userService.updateUserInfo(user);
            if (thongbao != null) {
                JOptionPane.showMessageDialog(dialog, thongbao, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "Cập nhật thông tin thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                loadUserInfo();
            }
        });
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }

    private void showChangePasswordDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 15, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Password fields với material design
        JPasswordField oldPass = new JPasswordField(20);
        JPasswordField newPass = new JPasswordField(20);
        JPasswordField confirmPass = new JPasswordField(20);

        // Style password fields
        for (JPasswordField field : new JPasswordField[]{oldPass, newPass, confirmPass}) {
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 0, 5, 0)
            ));
        }

        // Add components
        JLabel[] labels = {
            new JLabel("Mật khẩu hiện tại:"),
            new JLabel("Mật khẩu mới:"),
            new JLabel("Xác nhận mật khẩu:")
        };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            labels[i].setForeground(new Color(100, 100, 100));
            gbc.gridy = i * 2;
            panel.add(labels[i], gbc);
            
            gbc.gridy = i * 2 + 1;
            switch (i) {
                case 0: panel.add(oldPass, gbc); break;
                case 1: panel.add(newPass, gbc); break;
                case 2: panel.add(confirmPass, gbc); break;
            }
        }

        // Custom option buttons
        JButton btnChange = new JButton("Đổi mật khẩu");
        btnChange.setBackground(accentColor);
        btnChange.setForeground(Color.WHITE);
        btnChange.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnChange.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        JButton btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(220, 220, 220));
        btnCancel.setForeground(new Color(100, 100, 100));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnChange);
        
        gbc.gridy = labels.length * 2 + 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttonPanel, gbc);

        // Create custom dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Đổi mật khẩu");
        dialog.setModal(true);
        dialog.setContentPane(panel);
        dialog.getRootPane().setDefaultButton(btnChange);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        // Add action listeners
        btnChange.addActionListener(e -> {
            String oldPassword = new String(oldPass.getPassword());
            String newPassword = new String(newPass.getPassword());
            String confirmPassword = new String(confirmPass.getPassword());

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu mới không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            UserService userService = new UserService();
            String message = userService.changeUserPassword(
                UserSession.currentUsername, 
                oldPassword, 
                newPassword, 
                confirmPassword
            );

            if (message == null) {
                JOptionPane.showMessageDialog(dialog, "Đổi mật khẩu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }
}