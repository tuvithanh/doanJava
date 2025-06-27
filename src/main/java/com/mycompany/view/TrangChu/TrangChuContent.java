/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.view.TrangChu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TrangChuContent extends JPanel {

    private BufferedImage originalImage;
    private JLabel lblBanner;

    public TrangChuContent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        try {
            // Load ảnh gốc 1 lần
            originalImage = ImageIO.read(new File("D:/Learning/HK5/java/Java tu hoc/doan/doanJava/src/main/java/images/banner.jpg")); // Đường dẫn của bạn
            lblBanner = new JLabel();
            lblBanner.setHorizontalAlignment(SwingConstants.CENTER);

            add(lblBanner, BorderLayout.CENTER);

            // Gọi resize lần đầu
            resizeBanner();

            // Lắng nghe khi panel resize
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    resizeBanner();
                }
            });

        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Không thể load ảnh banner.");
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(errorLabel, BorderLayout.CENTER);
            e.printStackTrace();
        }
    }

    private void resizeBanner() {
        if (originalImage == null) return;

        int panelWidth = getWidth();

        if (panelWidth <= 0) return;

        int imgOriginalWidth = originalImage.getWidth();
        int imgOriginalHeight = originalImage.getHeight();

        // Tính chiều cao tương ứng để giữ tỉ lệ
        int scaledHeight = (panelWidth * imgOriginalHeight) / imgOriginalWidth;

        Image scaledImage = originalImage.getScaledInstance(panelWidth, scaledHeight, Image.SCALE_SMOOTH);
        lblBanner.setIcon(new ImageIcon(scaledImage));
    }
}



