/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.view.ThongKe;

/**
 *
 * @author VITHANH
 */
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import com.mycompany.dao.ThongKeDao;

public class DoanhThuPanel extends JPanel {

    public DoanhThuPanel() {
        setLayout(new BorderLayout());
        ChartPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        double tongDoanhThu = tinhTongDoanhThu();
        JLabel label = new JLabel("Tổng doanh thu: " + formatCurrency(tongDoanhThu));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        add(label, BorderLayout.SOUTH);
    }

    private ChartPanel createChartPanel() {
        ThongKeDao dao = new ThongKeDao();
        Map<String, Double> data = dao.getDoanhThuTheoSanPham();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Thống kê doanh thu theo sản phẩm",
                "Sản phẩm",
                "Doanh thu (VNĐ)",
                dataset
        );

        return new ChartPanel(chart);
    }

    private double tinhTongDoanhThu() {
        ThongKeDao dao = new ThongKeDao();
        Map<String, Double> data = dao.getDoanhThuTheoSanPham();
        double total = 0;
        for (double value : data.values()) {
            total += value;
        }
        return total;
    }
    private String formatCurrency(double amount) {
        NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        return vnFormat.format(amount) + " VNĐ";
    }

}


