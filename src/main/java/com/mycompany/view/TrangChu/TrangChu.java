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
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author VITHANH
 */
public class TrangChu extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TrangChu.class.getName());

    /**
     * Creates new form TrangChu
     */
    int width = 200;
    private boolean isMenuOpen = false;

    public TrangChu() {
        initComponents();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("MyStore");
        setLocationRelativeTo(null);

        // Đặt menuBar nằm ở trên contentPanel
        getLayeredPane().add(menuBar, JLayeredPane.POPUP_LAYER);
        menuBar.setBounds(0, 0, 0, getHeight());

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new TrangChuContent(), BorderLayout.CENTER);

        if (UserSession.currentUsername != null) {
            helloLabel.setText("Xin chào, " + UserSession.currentUsername);
            jSeparator2.setVisible(true);
            Logout_menubarlabel.setVisible(true);
        } else {
            jSeparator2.setVisible(false);
            Logout_menubarlabel.setVisible(false);
        }

        // Popup menu cho helloLabel
        UserService userSer = new UserService();
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem itemThongTin = new JMenuItem("Thông tin tài khoản");
        JMenuItem itemQLUSER = new JMenuItem("Quản lý User");
        JMenuItem itemQLCATE = new JMenuItem("Quản lý Category");
        JMenuItem itemQLPRODUCT = new JMenuItem("Quản lý Product");
        JMenuItem itemDOANHTHU = new JMenuItem("Doanh Thu");
        JMenuItem itemDangXuat = new JMenuItem("Đăng xuất");

        helloLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupMenu.removeAll();

                if (UserSession.currentUsername == null) {
                    new loginform().setVisible(true);
                    dispose();
                    return;
                }

                User user = userSer.getUserByUserName(UserSession.currentUsername);

                popupMenu.add(itemThongTin);
                if (user.getRole() == 'A') {
                    popupMenu.addSeparator();
                    popupMenu.add(itemQLUSER);
                    popupMenu.add(itemQLCATE);
                    popupMenu.add(itemQLPRODUCT);
                    popupMenu.add(itemDOANHTHU);
                }

                popupMenu.addSeparator();
                popupMenu.add(itemDangXuat);
                popupMenu.show(helloLabel, 0, helloLabel.getHeight());
            }
        });

        itemThongTin.addActionListener(evt -> {
            if (UserSession.currentUsername == null) {
                new loginform().setVisible(true);
                dispose();
            } else {
                contentPanel.removeAll();
                contentPanel.add(new Thongtintaikhoan(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        itemQLUSER.addActionListener(evt -> new QuanLyUser().setVisible(true));
        itemQLCATE.addActionListener(evt -> new QuanLyCategory().setVisible(true));
        itemQLPRODUCT.addActionListener(evt -> new QuanLyProduct().setVisible(true));

        itemDangXuat.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có chắc muốn đăng xuất?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                UserSession.delete();
                CredentialManager.clearLogin();
                new loginform().setVisible(true);
                dispose();
            }
        });
        itemDOANHTHU.addActionListener(evt -> {
            contentPanel.removeAll();
            contentPanel.add(new com.mycompany.view.ThongKe.DoanhThuPanel(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        // Resize cập nhật lại menu
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                menuBar.setBounds(0, 0, menuBar.getWidth(), getHeight());
            }
        });

        // Bắt sự kiện icon menu
        menu_icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMenuBar();
            }
        });

        exit_icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeMenuBar();
            }
        });
        
        contentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Nếu đang mở menu mà click ra ngoài menu → thì đóng lại
                if (isMenuOpen && e.getX() > menuBar.getWidth()) {
                    closeMenuBar();
                }
            }
        });
        
        // Sự kiện tìm kiếm
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                if (!searchText.isEmpty()) {
                    contentPanel.removeAll();
                    ProductView productView = new ProductView();
                    productView.searchProducts(searchText);
                    contentPanel.add(productView, BorderLayout.CENTER);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
            }
        });
    }

    void openMenuBar() {
        isMenuOpen = true;
        new Thread(() -> {
            int frameHeight = getHeight();
            for (int i = 0; i <= width; i++) {
                menuBar.setBounds(0, 0, i, frameHeight);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void closeMenuBar() {
        isMenuOpen = false;
        new Thread(() -> {
            int frameHeight = getHeight();
            for (int i = width; i >= 0; i--) {
                menuBar.setBounds(0, 0, i, frameHeight);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
        Favorite_menubarlabel = new javax.swing.JLabel();
        topNav = new javax.swing.JPanel();
        helloLabel = new javax.swing.JLabel();
        menu_icon = new javax.swing.JLabel();
        shoppingCart_icon = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
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

        Favorite_menubarlabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Favorite_menubarlabel.setText("Yêu thích");
        Favorite_menubarlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Favorite_menubarlabelMouseClicked(evt);
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
                                    .addComponent(Product_menubarlabel)
                                    .addComponent(Favorite_menubarlabel))))
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
                .addGap(38, 38, 38)
                .addComponent(Favorite_menubarlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(Logout_menubarlabel)
                .addContainerGap())
        );

        Favorite_menubarlabel.getAccessibleContext().setAccessibleName("Yêu Thích");

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

        searchField.setColumns(20);

        searchButton.setText("Tìm kiếm");

        javax.swing.GroupLayout topNavLayout = new javax.swing.GroupLayout(topNav);
        topNav.setLayout(topNavLayout);
        topNavLayout.setHorizontalGroup(
        topNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(topNavLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(menu_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(helloLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE) // Tăng độ dài lên 400
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(searchButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(shoppingCart_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    topNavLayout.setVerticalGroup(
        topNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(topNavLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(topNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(shoppingCart_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(topNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(menu_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(helloLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
    }// </editor-fold>                        

    private void menu_iconMouseClicked(java.awt.event.MouseEvent evt) {                                      
        openMenuBar();
    }                                     

    private void exit_iconMouseClicked(java.awt.event.MouseEvent evt) {                                      
        closeMenuBar();
    }                                     

    private void Home_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {                                              
        contentPanel.removeAll();
        contentPanel.add(new TrangChuContent(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }                                             

    private void Account_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {                                                 
        if (UserSession.currentUsername == null) {
            new loginform().setVisible(true);
            this.dispose();
            return;
        }
        else{
            contentPanel.removeAll();
            contentPanel.add(new Thongtintaikhoan(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }                                                

    private void Logout_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {                                                
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                UserSession.delete();
                CredentialManager.clearLogin();
                new loginform().setVisible(true);
                this.dispose();
            }
    }                                               

    private void helloLabelMouseClicked(java.awt.event.MouseEvent evt) {                                       
        
    }                                      

    private void shoppingCart_iconMouseClicked(java.awt.event.MouseEvent evt) {                                              
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
            contentPanel.add(new Cart(user.getId(), contentPanel), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }                                             

    private void Favorite_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {                                                  
        contentPanel.removeAll();
        contentPanel.add(new com.mycompany.view.Favorite.FavoriteView(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }                                                 

    private void Product_menubarlabelMouseClicked(java.awt.event.MouseEvent evt) {                                                 
        contentPanel.removeAll();
        contentPanel.add(new ProductView(), BorderLayout.CENTER);
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
    
    public JPanel getContentPanel() {
        return contentPanel;
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel Account_menubarlabel;
    private javax.swing.JLabel Favorite_menubarlabel;
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
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel shoppingCart_icon;
    private javax.swing.JLabel storeName_menubarlabel;
    private javax.swing.JPanel topNav;
    // End of variables declaration                   
}