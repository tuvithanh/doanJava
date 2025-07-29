package com.mycompany.view.Thongtintaikhoan;

import com.mycompany.model.User;
import com.mycompany.service.Admin.UserService;

import javax.swing.*;
import java.awt.*;

public class EditUserInfoDialog extends JDialog {
    private JTextField txtName, txtPhone, txtEmail, txtAddress;
    private JTextArea txtAbout;
    private User user;

    public EditUserInfoDialog(User user) {
        this.user = user;
        setTitle("Chỉnh sửa thông tin");
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        txtName = new JTextField(user.getName());
        txtPhone = new JTextField(user.getPhone());
        txtEmail = new JTextField(user.getEmail());
        txtAddress = new JTextField(user.getAddress());
        txtAbout = new JTextArea(user.getAbout());

        add(new JLabel("Họ tên:")); add(txtName);
        add(new JLabel("SĐT:")); add(txtPhone);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel("Địa chỉ:")); add(txtAddress);
        add(new JLabel("Giới thiệu:")); add(new JScrollPane(txtAbout));

        JButton btnSave = new JButton("Lưu");
        btnSave.addActionListener(e -> {
            user.setName(txtName.getText());
            user.setPhone(txtPhone.getText());
            user.setEmail(txtEmail.getText());
            user.setAddress(txtAddress.getText());
            user.setAbout(txtAbout.getText());

            new UserService().updateUserInfo(user);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            dispose();
        });

        add(new JLabel()); add(btnSave);
    }
}

