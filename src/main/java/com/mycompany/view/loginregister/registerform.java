import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class registerform extends JFrame {
    public registerform() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblFullName = new JLabel("Tên người dùng:");
        lblFullName.setBounds(30, 20, 120, 25);
        add(lblFullName);

        JTextField txtFullName = new JTextField();
        txtFullName.setBounds(160, 20, 200, 25);
        add(txtFullName);

        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setBounds(30, 60, 120, 25);
        add(lblPhone);

        JTextField txtPhone = new JTextField();
        txtPhone.setBounds(160, 60, 200, 25);
        add(txtPhone);

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setBounds(30, 100, 120, 25);
        add(lblUsername);

        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(160, 100, 200, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setBounds(30, 140, 120, 25);
        add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 140, 200, 25);
        add(txtPassword);

        JLabel lblRePassword = new JLabel("Nhập lại mật khẩu:");
        lblRePassword.setBounds(30, 180, 120, 25);
        add(lblRePassword);

        JPasswordField txtRePassword = new JPasswordField();
        txtRePassword.setBounds(160, 180, 200, 25);
        add(txtRePassword);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 220, 120, 25);
        add(lblEmail);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(160, 220, 200, 25);
        add(txtEmail);

        JLabel lblAddress = new JLabel("Địa chỉ:");
        lblAddress.setBounds(30, 260, 120, 25);
        add(lblAddress);

        JTextField txtAddress = new JTextField();
        txtAddress.setBounds(160, 260, 200, 25);
        add(txtAddress);

        JLabel lblMoreInfo = new JLabel("Thông tin thêm:");
        lblMoreInfo.setBounds(30, 300, 120, 25);
        add(lblMoreInfo);

        JTextArea txtMoreInfo = new JTextArea();
        txtMoreInfo.setLineWrap(true);
        txtMoreInfo.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtMoreInfo);
        scrollPane.setBounds(160, 300, 200, 60);
        add(scrollPane);

        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setBounds(150, 380, 100, 30);
        add(btnRegister);

        JButton btnBack = new JButton("Quay lại");
        btnBack.setBounds(260, 380, 100, 30);
        add(btnBack);

        btnBack.addActionListener(e -> {
            dispose(); // Đóng form đăng ký
            new loginform().setVisible(true); // Quay lại LoginForm
        });

        // ==== XỬ LÝ ĐĂNG KÝ ====
        btnRegister.addActionListener(e -> {
            String fullName = txtFullName.getText().trim();
            String phone = txtPhone.getText().trim();
            String username = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());
            String rePass = new String(txtRePassword.getPassword());
            String email = txtEmail.getText().trim();
            String address = txtAddress.getText().trim();

            // Kiểm tra rỗng
            if (fullName.isEmpty() || phone.isEmpty() || username.isEmpty()
                    || pass.isEmpty() || rePass.isEmpty()
                    || email.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra mật khẩu khớp
            if (!pass.equals(rePass)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại chỉ chứa số
            if (!phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được chứa chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra định dạng email
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Nếu mọi thứ hợp lệ
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            // TODO: Lưu vào CSDL nếu cần
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new registerform().setVisible(true);
        });
    }
}
