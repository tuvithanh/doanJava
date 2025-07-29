package com.mycompany.view.Thongtintaikhoan;

import com.mycompany.service.Admin.UserService;
import com.mycompany.sesion.UserSession.UserSession;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordPanel extends JPanel {
    public ChangePasswordPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel lblOld = new JLabel("Mật khẩu cũ:");
        JPasswordField txtOld = new JPasswordField();

        JLabel lblNew = new JLabel("Mật khẩu mới:");
        JPasswordField txtNew = new JPasswordField();

        JLabel lblConfirm = new JLabel("Xác nhận mật khẩu mới:");
        JPasswordField txtConfirm = new JPasswordField();

        JButton btnUpdate = new JButton("Cập nhật");
        btnUpdate.addActionListener(e -> {
            String oldPass = new String(txtOld.getPassword());
            String newPass = new String(txtNew.getPassword());
            String confirmPass = new String(txtConfirm.getPassword());

            UserService userService = new UserService();
            String username = UserSession.currentUsername;

            if (!userService.checkPassword(username, oldPass)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng.");
                return;
            }
            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp.");
                return;
            }
            userService.updatePassword(username, newPass);
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công.");
        });

        add(lblOld); add(txtOld);
        add(lblNew); add(txtNew);
        add(lblConfirm); add(txtConfirm);
        add(Box.createVerticalStrut(10));
        add(btnUpdate);
    }
}
