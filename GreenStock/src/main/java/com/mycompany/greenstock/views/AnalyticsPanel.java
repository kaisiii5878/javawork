package com.mycompany.greenstock.views;

import javax.swing.*;
import java.awt.*;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import com.mycompany.greenstock.controllers.SaleController;
import com.mycompany.greenstock.controllers.InventoryController;
import com.mycompany.greenstock.controllers.CustomerController;
import com.mycompany.greenstock.database.models.Sale;
import com.mycompany.greenstock.database.models.Inventory;
import com.mycompany.greenstock.database.models.Customer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class AnalyticsPanel extends JPanel {
    private final SaleController saleController;
    private final InventoryController inventoryController;
    private final CustomerController customerController;

    private JPanel salesChartPanel;
    private JPanel inventoryChartPanel;
    private JPanel customerChartPanel;
    private JPanel revenueChartPanel;

    public AnalyticsPanel(SaleController saleController, InventoryController inventoryController, CustomerController customerController) {
        this.saleController = saleController;
        this.inventoryController = inventoryController;
        this.customerController = customerController;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 250, 240));

        // Header Panel
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(34, 139, 34), getWidth(), 0, new Color(50, 205, 50));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("📊 Analytics Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(title, BorderLayout.WEST);

        // Add Generate Button
        JButton generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        generateButton.setBackground(new Color(0, 100, 0));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(e -> generateAnalytics());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));
        buttonPanel.add(generateButton);

        headerPanel.add(buttonPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 250, 240));

        // Sales Chart Panel
        salesChartPanel = createCardPanel("Sales Overview", new Color(144, 238, 144));
        contentPanel.add(salesChartPanel);

        // Inventory Chart Panel
        inventoryChartPanel = createCardPanel("Inventory Status", new Color(152, 251, 152));
        contentPanel.add(inventoryChartPanel);

        // Customer Chart Panel
        customerChartPanel = createCardPanel("Customer Insights", new Color(144, 238, 144));
        contentPanel.add(customerChartPanel);

        // Revenue Chart Panel
        revenueChartPanel = createCardPanel("Revenue Trends", new Color(152, 251, 152));
        contentPanel.add(revenueChartPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createCardPanel(String title, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 0), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(label, BorderLayout.NORTH);

        return panel;
    }

    private void generateAnalytics() {
        try {
            // Fetch data from controllers
            List<Sale> sales = saleController.getAllSales();
            List<Inventory> inventory = inventoryController.getAllInventory();
            List<Customer> customers = customerController.getAllCustomers();

            // Generate and render charts in existing panels
            renderSalesChart(sales, salesChartPanel);
            renderInventoryChart(inventory, inventoryChartPanel);
            renderCustomerChart(customers, customerChartPanel);
            renderRevenueChart(sales, revenueChartPanel);

            JOptionPane.showMessageDialog(this, "Analytics generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating analytics: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void renderSalesChart(List<Sale> sales, JPanel panel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Sale sale : sales) {
            dataset.addValue(sale.getTotalAmount(), "Sales", sale.getSaleDate().toString());
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Sales Overview", // Chart title
            "Date", // X-axis label
            "Total Amount", // Y-axis label
            dataset // Data
        );

        updatePanelWithChart(panel, chart);
    }

    private void renderInventoryChart(List<Inventory> inventory, JPanel panel) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Inventory item : inventory) {
            dataset.setValue(item.getPlantId() + " - " + item.getPotSize(), item.getQuantity());
        }

        JFreeChart chart = ChartFactory.createPieChart(
            "Inventory Status", // Chart title
            dataset, // Data
            true, // Include legend
            true,
            false
        );

        updatePanelWithChart(panel, chart);
    }

    private void renderCustomerChart(List<Customer> customers, JPanel panel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Customer customer : customers) {
            dataset.addValue(1, "Customers", customer.getName());
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Customer Insights", // Chart title
            "Customer", // X-axis label
            "Count", // Y-axis label
            dataset // Data
        );

        updatePanelWithChart(panel, chart);
    }

    private void renderRevenueChart(List<Sale> sales, JPanel panel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Sale sale : sales) {
            dataset.addValue(sale.getTotalAmount(), "Revenue", sale.getSaleDate().toString());
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Revenue Trends", // Chart title
            "Date", // X-axis label
            "Total Amount", // Y-axis label
            dataset // Data
        );

        updatePanelWithChart(panel, chart);
    }

    private void updatePanelWithChart(JPanel panel, JFreeChart chart) {
        panel.removeAll(); // Clear existing components
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
} 