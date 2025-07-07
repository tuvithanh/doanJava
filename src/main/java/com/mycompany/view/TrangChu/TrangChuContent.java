/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.view.TrangChu;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author VITHANH
 */
public class TrangChuContent extends javax.swing.JPanel {
    private JPanel danhMucPanel;
    private JPanel sanphamPanel;
    /**
     * Creates new form TrangChuContent
     */
    public TrangChuContent() {
        initComponents();
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        danhMucPanel = new JPanel();
        danhMucPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(danhMucPanel, BorderLayout.CENTER);
        
        
        sanphamPanel = new JPanel();
        sanphamPanel.setLayout(new BorderLayout());
        panelSanPham.add(sanphamPanel, BorderLayout.CENTER);
        
        banner.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                loadBanner();
            }
        });
        
        loadBanner();
        loadDanhMuc();
        loadSanPham();
    }
    
    private void loadBanner() {
        try {
            ImageIcon originalIcon = new ImageIcon("src/main/java/images/banner.jpg");
            Image img = originalIcon.getImage();

            int bannerWidth = banner.getWidth();
            if (bannerWidth <= 0) return; // Chưa hiển thị → không làm gì

            // Tính chiều cao theo tỉ lệ ảnh gốc
            int scaledHeight = (bannerWidth * originalIcon.getIconHeight()) / originalIcon.getIconWidth();
            Image resized = img.getScaledInstance(bannerWidth, scaledHeight, Image.SCALE_SMOOTH);

            banner.setIcon(new ImageIcon(resized));
            banner.setText(""); // Xóa thông báo cũ (nếu có)
        } catch (Exception ex) {
            banner.setText("Không thể load ảnh banner.");
        }
    }
    
    private void loadDanhMuc() {
        String[] danhMuc = {"Tất cả", "Trà sữa", "Cà phê", "Sinh tố", "Khác"};
        for (String ten : danhMuc) {
            JButton btn = new JButton(ten);
            btn.setBackground(new Color(220, 220, 220));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            danhMucPanel.add(btn);
        }
    }
    
    private void loadSanPham() {
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setBackground(Color.WHITE);
        horizontalPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (int i = 1; i <= 8; i++) {
            String tenSP = "Sản phẩm " + i;
            String gia = (750000 + i * 10000) + "đ";
            String moTa = "Loại sản phẩm";
            String pathAnh = "src/main/java/images/null.png"; // Đặt ảnh tại đây

            JPanel sp = createSanPhamPanel(tenSP, gia, moTa, pathAnh);
            horizontalPanel.add(sp);
            horizontalPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        }

        JScrollPane scrollPane = new JScrollPane(horizontalPanel,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);

        sanphamPanel.removeAll();
        sanphamPanel.setLayout(new BorderLayout());
        sanphamPanel.add(scrollPane, BorderLayout.CENTER);
        sanphamPanel.revalidate();
        sanphamPanel.repaint();
    }

    private JPanel createSanPhamPanel(String tenSP, String gia, String moTa, String pathAnh) {
        JPanel spPanel = new JPanel();
        spPanel.setLayout(new BoxLayout(spPanel, BoxLayout.Y_AXIS));
        spPanel.setPreferredSize(new Dimension(200, 200));
        spPanel.setBackground(Color.WHITE);
        spPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Ảnh
        ImageIcon icon = new ImageIcon(pathAnh); // ảnh từ file
        Image img = icon.getImage().getScaledInstance(160, 120, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Giá
        JLabel lblGia = new JLabel(gia);
        lblGia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGia.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblGia.setForeground(Color.BLACK);
        lblGia.setBorder(new EmptyBorder(5, 0, 0, 0));

        // Tên SP
        JLabel lblTen = new JLabel(tenSP);
        lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mô tả
        JLabel lblMoTa = new JLabel(moTa);
        lblMoTa.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblMoTa.setForeground(Color.GRAY);
        lblMoTa.setAlignmentX(Component.CENTER_ALIGNMENT);

        spPanel.add(Box.createVerticalStrut(10));
        spPanel.add(imgLabel);
        spPanel.add(lblGia);
        spPanel.add(lblTen);
        spPanel.add(lblMoTa);

        return spPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        banner = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelSanPham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        jPanel3.setPreferredSize(new java.awt.Dimension(1230, 619));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        banner.setText("banner");
        banner.setToolTipText("");
        banner.setPreferredSize(new java.awt.Dimension(1230, 619));
        jPanel3.add(banner, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1510, 250));

        panelSanPham.setAutoscrolls(true);
        panelSanPham.setLayout(new javax.swing.BoxLayout(panelSanPham, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane1.setViewportView(panelSanPham);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("DANH MỤC");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1510, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel banner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelSanPham;
    // End of variables declaration//GEN-END:variables
}
