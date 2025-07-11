package com.mycompany.view.TrangChu;

import com.mycompany.dao.CategoryDao;
import com.mycompany.dao.ProductDao;
import com.mycompany.model.Category;
import com.mycompany.model.Product;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TrangChuContent extends JPanel {
    private JPanel danhMucPanel;
    private JPanel sanphamPanel;
    private JScrollPane mainScrollPane;
    private JPanel contentPanel;
    private JPanel panelSanPham;
    private JLabel jLabel1;
    private JPanel bannerPanel;
    private JLabel banner;

    private Color primaryColor = new Color(0, 102, 204);
    private Color hoverColor = new Color(0, 122, 255);
    private Color backgroundColor = new Color(245, 245, 245);

    public TrangChuContent() {
        initComponents();
        setupUI();
    }

    private void initComponents() {
        // Main scroll pane for the entire content
        mainScrollPane = new JScrollPane();
        mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Main content panel that will hold all components
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(backgroundColor);
        
        // Banner panel
        bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBackground(backgroundColor);
        banner = new JLabel();
        banner.setHorizontalAlignment(SwingConstants.CENTER);
        bannerPanel.add(banner, BorderLayout.CENTER);
        
        // Set banner panel with maximum width and proportional height
        bannerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400)); // Max height 400px
        bannerPanel.setPreferredSize(new Dimension(1200, 400)); // Default size
        
        // Product title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        titlePanel.setBackground(backgroundColor);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        jLabel1 = new JLabel("SẢN PHẨM NỔI BẬT");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setForeground(primaryColor);
        titlePanel.add(jLabel1);
        
        // Category panel
        danhMucPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        danhMucPanel.setBackground(backgroundColor);
        danhMucPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        // Products panel
        panelSanPham = new JPanel();
        panelSanPham.setLayout(new BoxLayout(panelSanPham, BoxLayout.Y_AXIS));
        panelSanPham.setBackground(backgroundColor);
        
        sanphamPanel = new JPanel(new BorderLayout());
        sanphamPanel.setBackground(backgroundColor);
        panelSanPham.add(sanphamPanel);
        
        // Add all components to content panel
        contentPanel.add(bannerPanel);
        contentPanel.add(titlePanel);
        contentPanel.add(danhMucPanel);
        contentPanel.add(panelSanPham);
        
        // Add some vertical spacing
        contentPanel.add(Box.createVerticalGlue());
        
        // Set the content panel as viewport view
        mainScrollPane.setViewportView(contentPanel);
        
        // Set layout for main panel
        setLayout(new BorderLayout());
        add(mainScrollPane, BorderLayout.CENTER);
        
        // Add component listener for banner resizing
        banner.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> loadBanner());
            }
        });
    }

    private void setupUI() {
        loadBanner();
        loadDanhMuc();
        loadSanPham(0);
    }

    private void loadBanner() {
        try {
            int width = bannerPanel.getWidth();
            if (width <= 0) return;

            ImageIcon originalIcon = new ImageIcon("src/main/java/images/banner.jpg");
            Image originalImage = originalIcon.getImage();
            
            // Calculate proportional height (max 400px)
            int height = Math.min(400, (width * originalIcon.getIconHeight()) / originalIcon.getIconWidth());
            
            // Resize image
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Create rounded image
            ImageIcon roundedIcon = new ImageIcon(createRoundedImage(scaledImage, 15));
            banner.setIcon(roundedIcon);
            banner.setText("");
            
            // Update banner panel size
            bannerPanel.setPreferredSize(new Dimension(width, height));
            bannerPanel.revalidate();
        } catch (Exception ex) {
            banner.setText("Không thể load ảnh banner");
            banner.setFont(new Font("Segoe UI", Font.BOLD, 16));
            banner.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }

    private Image createRoundedImage(Image image, int cornerRadius) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        if (width <= 0 || height <= 0) {
            return image;
        }

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        return output;
    }

    private void loadDanhMuc() {
        JButton btnDanhMuc = new JButton("DANH MỤC SẢN PHẨM ▾");
        btnDanhMuc.setFocusPainted(false);
        btnDanhMuc.setBackground(primaryColor);
        btnDanhMuc.setForeground(Color.WHITE);
        btnDanhMuc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDanhMuc.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnDanhMuc.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDanhMuc.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnDanhMuc.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent e) {
                btnDanhMuc.setBackground(primaryColor);
            }
        });

        JPopupMenu popup = new JPopupMenu();
        popup.setPreferredSize(new Dimension(250, 300));
        popup.setLayout(new BorderLayout());
        popup.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primaryColor);
        header.setPreferredSize(new Dimension(250, 40));
        JLabel title = new JLabel("CHỌN DANH MỤC", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.add(title, BorderLayout.CENTER);
        popup.add(header, BorderLayout.NORTH);

        JPanel danhMucList = new JPanel();
        danhMucList.setLayout(new BoxLayout(danhMucList, BoxLayout.Y_AXIS));
        danhMucList.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(danhMucList);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getAllCategory();

        JButton tatCa = createCategoryButton("TẤT CẢ SẢN PHẨM");
        tatCa.addActionListener(e -> {
            popup.setVisible(false);
            loadSanPham(0);
        });
        danhMucList.add(tatCa);
        danhMucList.add(Box.createRigidArea(new Dimension(0, 5)));
        danhMucList.add(new JSeparator());
        danhMucList.add(Box.createRigidArea(new Dimension(0, 5)));

        for (Category cate : categories) {
            JButton btn = createCategoryButton(cate.getName().toUpperCase());
            btn.addActionListener(e -> {
                popup.setVisible(false);
                loadSanPham(cate.getId());
            });
            danhMucList.add(btn);
            danhMucList.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        popup.add(scroll, BorderLayout.CENTER);
        btnDanhMuc.addActionListener(e -> popup.show(btnDanhMuc, 0, btnDanhMuc.getHeight()));

        danhMucPanel.removeAll();
        danhMucPanel.add(btnDanhMuc);
    }

    private JButton createCategoryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(240, 240, 240));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }

    private void loadSanPham(int cateId) {
        ProductDao productDao = new ProductDao();
        List<Product> products = cateId == 0 ? productDao.getAllProducts() : productDao.getProductsByCategory(cateId);

        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setBackground(backgroundColor);
        horizontalPanel.setBorder(new EmptyBorder(20, 20, 40, 20)); // Added bottom padding

        if (products.isEmpty()) {
            JLabel emptyLabel = new JLabel("Không có sản phẩm nào trong danh mục này", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            horizontalPanel.add(emptyLabel);
        } else {
            for (Product p : products) {
                JPanel sp = createSanPhamPanel(p);
                horizontalPanel.add(sp);
                horizontalPanel.add(Box.createRigidArea(new Dimension(20, 0)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(horizontalPanel,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.setBackground(backgroundColor);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(40);
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        sanphamPanel.removeAll();
        sanphamPanel.setLayout(new BorderLayout());
        sanphamPanel.add(scrollPane, BorderLayout.CENTER);
        sanphamPanel.revalidate();
        sanphamPanel.repaint();
    }

    private JPanel createSanPhamPanel(Product p) {
        JPanel spPanel = new JPanel();
        spPanel.setLayout(new BoxLayout(spPanel, BoxLayout.Y_AXIS));
        spPanel.setPreferredSize(new Dimension(220, 320));
        spPanel.setMaximumSize(new Dimension(220, 320));
        spPanel.setBackground(Color.WHITE);

        Border border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10));

        spPanel.setBorder(border);

        spPanel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                spPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(primaryColor),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            }

            public void mouseExited(MouseEvent e) {
                spPanel.setBorder(border);
            }
        });

        try {
            ImageIcon icon = new ImageIcon(p.getImagePath());
            Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            Image roundedImg = createRoundedImage(img, 10);

            JLabel imgLabel = new JLabel(new ImageIcon(roundedImg));
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imgLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lblGia = new JLabel("₫" + String.format("%,.0f", p.getPrice()));
            lblGia.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblGia.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblGia.setForeground(primaryColor);

            JLabel lblTen = new JLabel("<html><center>" + p.getName() + "</center></html>");
            lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTen.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel lblMoTa = new JLabel("<html><center>" + p.getDescription() + "</center></html>");
            lblMoTa.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            lblMoTa.setForeground(Color.GRAY);
            lblMoTa.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblMoTa.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            spPanel.add(imgLabel);
            spPanel.add(Box.createVerticalStrut(5));
            spPanel.add(lblGia);
            spPanel.add(Box.createVerticalStrut(5));
            spPanel.add(lblTen);
            spPanel.add(Box.createVerticalStrut(5));
            spPanel.add(lblMoTa);
        } catch (Exception ex) {
            JLabel errorLabel = new JLabel("Lỗi tải sản phẩm", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            spPanel.add(errorLabel);
        }

        return spPanel;
    }
}

class CustomScrollBarUI extends BasicScrollBarUI {
    private final Color THUMB_COLOR = new Color(150, 150, 150);
    private final Color THUMB_HOVER_COLOR = new Color(100, 100, 100);
    private final int THUMB_SIZE = 8;

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createInvisibleButton();
    }

    private JButton createInvisibleButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(240, 240, 240));
        g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 10, 10);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(isDragging || isThumbRollover() ? THUMB_HOVER_COLOR : THUMB_COLOR);
        g2.fillRoundRect(
                thumbBounds.x + (thumbBounds.width - THUMB_SIZE) / 2,
                thumbBounds.y,
                THUMB_SIZE,
                thumbBounds.height,
                THUMB_SIZE,
                THUMB_SIZE
        );
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }
}