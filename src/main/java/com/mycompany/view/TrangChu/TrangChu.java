/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.view.TrangChu;

import javax.swing.ImageIcon;
import java.awt.Image; 
import com.mycompany.view.TrangChu.TrangChu;
import com.mycompany.view.Account.loginform;
import com.mycompany.CredentialManager.CredentialManager;
import java.net.URL;
import com.mycompany.sesion.UserSession.UserSession;
import com.mycompany.view.Thongtintaikhoan.Thongtintaikhoan;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import com.mycompany.service.Admin.UserService;
import com.mycompany.model.User;
import com.mycompany.view.Admin.QLUSER.QuanLyUser;
import com.mycompany.view.Admin.QLCATEGORY.QuanLyCategory;
import com.mycompany.view.Admin.QLPRODUCT.QuanLyProduct;
import com.mycompany.view.Cart.Cart;
import com.mycompany.view.SanPham.ProductView;
import javax.swing.JPanel;
/**
 *
 * @author VITHANH
 */
public class TrangChu extends javax.swing.JFrame {
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TrangChu.class.getName());

    /**
     * Creates new form TrangChu
     */
    public TrangChu() {
        initComponents();
        contentPanel.removeAll();
        contentPanel.add(new TrangChuContent());
        contentPanel.revalidate();
        contentPanel.repaint();
        
        //popupmenu
        UserService userSer = new UserService();
        
        // Tạo popup menu
        JPopupMenu popupMenu = new JPopupMenu();

        // Tạo các menu item
        JMenuItem itemThongTin = new JMenuItem("Thông tin tài khoản");
        JMenuItem itemQLUSER = new JMenuItem("Quản lý User");
        JMenuItem itemQLCATE = new JMenuItem("Quản lý Category");
        JMenuItem itemQLPRODUCT = new JMenuItem("Quản lý Product");
        JMenuItem itemDangXuat = new JMenuItem("Đăng xuất");

    // Gắn sự kiện click vào helloLabel
    helloLabel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            popupMenu.removeAll();

            // Nếu chưa đăng nhập → mở login form
            if (UserSession.currentUsername == null) {
                new loginform().setVisible(true);
                return;
            }

            // Nếu đã đăng nhập → hiển thị menu theo vai trò
            User user = userSer.getUserByUserName(UserSession.currentUsername);

            popupMenu.add(itemThongTin);

            if (user.getRole() == 'A') {
                popupMenu.addSeparator();
                popupMenu.add(itemQLUSER);
                popupMenu.add(itemQLCATE);
                popupMenu.add(itemQLPRODUCT);
            }

            popupMenu.addSeparator();
            popupMenu.add(itemDangXuat);

            // Hiển thị popup ngay bên dưới helloLabel
            popupMenu.show(helloLabel, 0, helloLabel.getHeight());
        }
    });

        // Xử lý chọn menu
        itemThongTin.addActionListener(evt -> {
                if (UserSession.currentUsername == null) {
                    new loginform().setVisible(true);
                    this.dispose();
                    return;
                }
                else{
                    User user = userSer.getUserByUserName(UserSession.currentUsername);
                    contentPanel.removeAll();
                    contentPanel.add(new Thongtintaikhoan());
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
        });

        itemQLUSER.addActionListener(evt -> {
            new QuanLyUser().setVisible(true);
        });
        itemQLCATE.addActionListener(evt -> {
            new QuanLyCategory().setVisible(true);
        });
        itemQLPRODUCT.addActionListener(evt -> {
            new QuanLyProduct().setVisible(true);
        });
        
        itemDangXuat.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                UserSession.delete();
                CredentialManager.clearLogin();
                new loginform().setVisible(true);
                this.dispose();
            }
        });

        
        setLocationRelativeTo(null);
        setTitle("MyStore");
//        ImageIcon icon = new ImageIcon(getClass().getResource("/images/mystore.png"));
//        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//        logo.setIcon(new ImageIcon(image));
//
//      logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        logo.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
// contentPane
        if (UserSession.currentUsername != null) {
            helloLabel.setText("Xin chào, " + UserSession.currentUsername);
            jSeparator2.setVisible(true);
            Logout_menubarlabel.setVisible(true);
        }
        else{
            jSeparator2.setVisible(false);
            Logout_menubarlabel.setVisible(false);
        }
        
    }
    int width = 170;
    
    void openMenuBar() {
    new Thread(() -> {
        int frameHeight = getHeight(); // 👈 lấy chiều cao thật sự
        for (int i = 0; i <= width; i++) {
            menuBar.setSize(i, frameHeight);
            try {
                Thread.sleep(1); // thêm delay để thấy hiệu ứng
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();
    }

    void closeMenuBar() {
        new Thread(() -> {
            int frameHeight = getHeight(); // 👈 cập nhật chiều cao mới
            for (int i = width; i >= 0; i--) {
                menuBar.setSize(i, frameHeight);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        storeName_menubarlabel = new javax.swing.JLabel();
        exit_icon = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Home_menubarlabel = new javax.swing.JLabel();
        Account_menubarlabel = new javax.swing.JLabel();
        Product_menubarlabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        Logout_menubarlabel = new javax.swing.JLabel();
        topNav = new javax.swing.JPanel();
        helloLabel = new javax.swing.JLabel();
        menu_icon = new javax.swing.JLabel();
        shoppingCart_icon = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuBar.setBackground(new java.awt.Color(226, 218, 214));
        menuBar.setForeground(new java.awt.Color(255, 255, 255));

        ImageIcon originalIcon = new ImageIcon("src/images/mystore.png");

        // Resize image to 100x100
        Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Set resized icon to the JLabel
        logo = new javax.swing.JLabel();
        logo.setIcon(resizedIcon);

        storeName_menubarlabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        storeName_menubarlabel.setText("My Store");

        originalIcon = new ImageIcon("src/images/exit.png");
        resizedImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);

        exit_icon = new javax.swing.JLabel();
        exit_icon.setIcon(resizedIcon);
        exit_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit_iconMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        Home_menubarlabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Home_menubarlabel.setText("Trang chủ");
        Home_menubarlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Home_menubarlabelMouseClicked(evt);
            }
        });

        Account_menubarlabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Account_menubarlabel.setText("Tài khoản");
        Account_menubarlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Account_menubarlabelMouseClicked(evt);
            }
        });

        Product_menubarlabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Product_menubarlabel.setText("Sản phẩm");
        Product_menubarlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Product_menubarlabelMouseClicked(evt);
            }
        });

        Logout_menubarlabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Logout_menubarlabel.setText("Đăng xuất");
        Logout_menubarlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Logout_menubarlabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menuBarLayout = new javax.swing.GroupLayout(menuBar);
        menuBar.setLayout(menuBarLayout);
        menuBarLayout.setHorizontalGroup(
            menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBarLayout.createSequentialGroup()
                .addGroup(menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(menuBarLayout.createSequentialGroup()
                        .addGroup(menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuBarLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(logo))
                            .addGroup(menuBarLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(storeName_menubarlabel))
                            .addGroup(menuBarLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Home_menubarlabel)
                                    .addComponent(Account_menubarlabel)
                                    .addComponent(Product_menubarlabel))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(menuBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuBarLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(exit_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator2))))
                .addContainerGap())
            .addGroup(menuBarLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(Logout_menubarlabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuBarLayout.setVerticalGroup(
            menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exit_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(logo)
                .addGap(18, 18, 18)
                .addComponent(storeName_menubarlabel)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(Home_menubarlabel)
                .addGap(32, 32, 32)
                .addComponent(Account_menubarlabel)
                .addGap(32, 32, 32)
                .addComponent(Product_menubarlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 237, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(Logout_menubarlabel)
                .addContainerGap())
        );

        topNav.setBackground(new java.awt.Color(100, 130, 173));

        helloLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        helloLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                helloLabelMouseClicked(evt);
            }
        });

        originalIcon = new ImageIcon("src/images/menu.png");
        resizedImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);

        menu_icon = new javax.swing.JLabel();
        menu_icon.setIcon(resizedIcon);
        menu_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_iconMouseClicked(evt);
            }
        });

        originalIcon = new ImageIcon("src/main/java/images/shoppingcart.png");
        resizedImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);

        shoppingCart_icon = new javax.swing.JLabel();
        shoppingCart_icon.setIcon(resizedIcon);
        shoppingCart_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shoppingCart_iconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout topNavLayout = new javax.swing.GroupLayout(topNav);
        topNav.setLayout(topNavLayout);
        topNavLayout.setHorizontalGroup(
            topNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topNavLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 928, Short.MAX_VALUE)
                .addComponent(helloLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(shoppingCart_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topNavLayout.setVerticalGroup(
            topNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topNavLayout.createSequentialGroup()
                .addGroup(topNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(shoppingCart_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, topNavLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(topNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(helloLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(menu_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contentPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menuBar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                    .addComponent(topNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_iconMouseClicked
        // TODO add your handling code here:
        
        openMenuBar();
    }//GEN-LAST:event_menu_iconMouseClicked

    private void exit_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit_iconMouseClicked
        // TODO add your handling code here:
        closeMenuBar();
    }//GEN-LAST:event_exit_iconMouseClicked

    private void Home_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Home_menubarlabelMouseClicked
        // TODO add your handling code here:
        contentPanel.removeAll();
        contentPanel.add(new TrangChuContent());
        contentPanel.revalidate();
        contentPanel.repaint();
    }//GEN-LAST:event_Home_menubarlabelMouseClicked

    private void Account_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account_menubarlabelMouseClicked
        // TODO add your handling code here:
        if (UserSession.currentUsername == null) {
            new loginform().setVisible(true);
            this.dispose();
            return;
        }
        else{
            contentPanel.removeAll();
            contentPanel.add(new Thongtintaikhoan());
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }//GEN-LAST:event_Account_menubarlabelMouseClicked

    private void Logout_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Logout_menubarlabelMouseClicked
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                UserSession.delete();
                CredentialManager.clearLogin();
                new loginform().setVisible(true);
                this.dispose();
            }
    }//GEN-LAST:event_Logout_menubarlabelMouseClicked

    private void helloLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helloLabelMouseClicked
        
    }//GEN-LAST:event_helloLabelMouseClicked

    private void shoppingCart_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shoppingCart_iconMouseClicked
        // TODO add your handling code here:
        if (UserSession.currentUsername == null) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Bạn cần phải đăng nhập.",
                "Đăng nhập",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (result == JOptionPane.OK_OPTION) {
                new loginform().setVisible(true);
                this.dispose();
            }
        }
        else{
            UserService userSer = new UserService();
            User user = userSer.getUserByUserName(UserSession.currentUsername);
            contentPanel.removeAll();
            contentPanel.add(new Cart(user.getId()));
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }//GEN-LAST:event_shoppingCart_iconMouseClicked

     private void Product_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {                                                 
        // TODO add your handling code here:
        contentPanel.removeAll();
        contentPanel.add(new ProductView());
        contentPanel.revalidate();
        contentPanel.repaint();
    }    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TrangChu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Account_menubarlabel;
    private javax.swing.JLabel Home_menubarlabel;
    private javax.swing.JLabel Logout_menubarlabel;
    private javax.swing.JLabel Product_menubarlabel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel exit_icon;
    private javax.swing.JLabel helloLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel menuBar;
    private javax.swing.JLabel menu_icon;
    private javax.swing.JLabel shoppingCart_icon;
    private javax.swing.JLabel storeName_menubarlabel;
    private javax.swing.JPanel topNav;
    // End of variables declaration//GEN-END:variables
}
