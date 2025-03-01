/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.greenstock.views;


import com.mycompany.greenstock.controllers.PlantController;
import com.mycompany.greenstock.controllers.NurseryController;
import com.mycompany.greenstock.controllers.InventoryController;
import com.mycompany.greenstock.controllers.CustomerController;
import com.mycompany.greenstock.controllers.SaleController;
import com.mycompany.greenstock.controllers.SaleItemController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author blackheart
 */
public class MainView extends javax.swing.JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton currentNavButton;

    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
        setupUI();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

       
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Stock Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       

        // Main content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel, BorderLayout.CENTER);

        // Adding placeholder sections
        

        pack();
        setVisible(true);
    }

    private void setupUI() {
        setTitle("GreenStock - Plant and Nursery Management");
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main container with BorderLayout
        setLayout(new BorderLayout());
        
        // Create header
        JPanel header = createHeader();
        add(header, BorderLayout.NORTH);
        
        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        
        // Create main content area
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(new Color(245, 250, 240)); // Light green background
        add(contentPanel, BorderLayout.CENTER);
        
        // Initialize panels
        initContentPanels();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(34, 139, 34), getWidth(), 0, new Color(50, 205, 50)); // Green gradient
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 60));
        header.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("GreenStock");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        header.add(title, BorderLayout.WEST);
        
        // Add user info and logout button
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setOpaque(false);
        
        JLabel userLabel = new JLabel("Welcome, Gardener");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutButton.setBackground(new Color(139, 69, 19)); // Brown color
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        
        userPanel.add(userLabel);
        userPanel.add(logoutButton);
        header.add(userPanel, BorderLayout.EAST);
        
        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 100, 0), getWidth(), 0, new Color(34, 139, 34)); // Dark green gradient
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setLayout(new GridLayout(0, 1, 0, 5));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        String[] sections = {
            "🌱 Plants", 
            "🏡 Nurseries", 
            "📦 Inventory", 
            "👥 Customers", 
            "💰 Sales", 
            "📊 Analytics"
        };

        for (String section : sections) {
            JButton btn = createNavButton(section);
            btn.addActionListener(new NavButtonListener(section.replaceAll("[^a-zA-Z]", "").toUpperCase()));
            sidebar.add(btn);
        }
        
        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setIconTextGap(15);
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(255, 255, 255, 20));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 0, 0, 0));
            }
        });
        
        return btn;
    }

    private void initContentPanels() {
        // Initialize controllers
        PlantController plantController = new PlantController();
        NurseryController nurseryController = new NurseryController();
        InventoryController inventoryController = new InventoryController();
        CustomerController customerController = new CustomerController();
        SaleController saleController = new SaleController();
        SaleItemController saleItemController = new SaleItemController();

        // Add panels with their respective controllers
        contentPanel.add(new PlantsPanel(plantController), "PLANTS");
        contentPanel.add(new NurseriesPanel(nurseryController), "NURSERIES");
        contentPanel.add(new InventoryPanel(inventoryController), "INVENTORY");
        contentPanel.add(new CustomersPanel(customerController), "CUSTOMERS");
        contentPanel.add(new SalesPanel(saleController, saleItemController), "SALES");
        contentPanel.add(new AnalyticsPanel(saleController,inventoryController,customerController), "ANALYTICS");
    }

    private class NavButtonListener implements ActionListener {
        private final String panelName;

        public NavButtonListener(String panelName) {
            this.panelName = panelName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentNavButton != null) {
                currentNavButton.setBackground(new Color(0, 0, 0, 0));
            }
            currentNavButton = (JButton) e.getSource();
            currentNavButton.setBackground(new Color(34, 139, 34)); // Green highlight
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, panelName);
        }
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainView().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
