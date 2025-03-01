package com.mycompany.greenstock.views;

import javax.swing.*;
import java.awt.*;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import com.mycompany.greenstock.controllers.SaleController;
import com.mycompany.greenstock.controllers.SaleItemController;
import com.mycompany.greenstock.database.models.Sale;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.util.List;

public class SalesPanel extends JPanel {
    private final SaleController saleController;
    private final SaleItemController saleItemController;
    private JPanel listPanel;
    private JPanel detailsPanel;
    private JTextField customerField, dateField, totalAmountField, itemsField;
    private Sale selectedSale;

    public SalesPanel(SaleController saleController, SaleItemController saleItemController) {
        this.saleController = saleController;
        this.saleItemController = saleItemController;
        initUI();
        loadSales();
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

        JLabel title = new JLabel("💰 Sales Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        // Create a panel for CRUD buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));

        JButton addButton = new JButton("Add Sale");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addButton.setBackground(new Color(0, 100, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Sale");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        editButton.setBackground(new Color(0, 100, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        buttonPanel.add(editButton);

        JButton delButton = new JButton("Delete Sale");
        delButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        delButton.setBackground(new Color(0, 100, 0));
        delButton.setForeground(Color.WHITE);
        delButton.setFocusPainted(false);
        buttonPanel.add(delButton);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 250, 240));

        // Sales List Panel
        listPanel = createCardPanel("Sales List", new Color(144, 238, 144));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Wrap the list panel in a scroll pane
        JScrollPane listScrollPane = new JScrollPane(listPanel);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(listScrollPane);

        // Sales Details Panel
        detailsPanel = createCardPanel("Sales Details", new Color(152, 251, 152));
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(152, 251, 152));

        customerField = new JTextField();
        dateField = new JTextField();
        totalAmountField = new JTextField();
        itemsField = new JTextField();

        formPanel.add(new JLabel("Customer:"));
        formPanel.add(customerField);
        formPanel.add(new JLabel("Date:"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Total Amount:"));
        formPanel.add(totalAmountField);
        formPanel.add(new JLabel("Items:"));
        formPanel.add(itemsField);

        detailsPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(detailsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        addButton.addActionListener(e -> addSale());
        editButton.addActionListener(e -> editSale());
        delButton.addActionListener(e -> deleteSale());
    }

    private void loadSales() {
        listPanel.removeAll();
        List<Sale> sales = saleController.getAllSales();
        for (Sale sale : sales) {
            JPanel saleCard = createSaleCard(sale);
            listPanel.add(saleCard);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createSaleCard(Sale sale) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 0), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(300, 80)); // Set fixed size for the card

        JLabel customerLabel = new JLabel("Customer ID: " + sale.getCustomerId());
        customerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(customerLabel, BorderLayout.CENTER);

        JLabel dateLabel = new JLabel("Date: " + sale.getSaleDate());
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(dateLabel, BorderLayout.SOUTH);

        // Add mouse listener to the card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedSale = sale;
                System.out.println("Selected Sale: " + sale.getId()); // Debugging
                highlightSelectedCard(card);
                showSaleDetails(sale); // Show details for the selected sale
            }
        });

        return card;
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

    private void highlightSelectedCard(JPanel selectedCard) {
        // Reset background for all cards
        for (Component component : listPanel.getComponents()) {
            if (component instanceof JPanel) {
                component.setBackground(Color.WHITE);
            }
        }
        // Highlight the selected card
        selectedCard.setBackground(new Color(200, 255, 200)); // Light green
    }

    private void showSaleDetails(Sale sale) {
        customerField.setText(String.valueOf(sale.getCustomerId()));
        dateField.setText(sale.getSaleDate().toString());
        totalAmountField.setText(String.valueOf(sale.getTotalAmount()));
        // Load sale items (if needed)
        // itemsField.setText(...); // You can populate this with sale items
    }

    private void addSale() {
        try {
            JTextField customerField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField totalAmountField = new JTextField();
            JTextField itemsField = new JTextField();

            Object[] message = {
                "Customer ID:", customerField,
                "Date:", dateField,
                "Total Amount:", totalAmountField,
                "Items:", itemsField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Add New Sale", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Sale newSale = new Sale();
                newSale.setCustomerId(Integer.parseInt(customerField.getText()));
                newSale.setSaleDate(java.sql.Timestamp.valueOf(dateField.getText()));
                newSale.setTotalAmount(Double.parseDouble(totalAmountField.getText()));

                String result = saleController.createSale(newSale);
                JOptionPane.showMessageDialog(this, result);
                loadSales();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding sale: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editSale() {
        if (selectedSale == null) {
            JOptionPane.showMessageDialog(this, "Please select a sale to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField customerField = new JTextField(String.valueOf(selectedSale.getCustomerId()));
        JTextField dateField = new JTextField(selectedSale.getSaleDate().toString());
        JTextField totalAmountField = new JTextField(String.valueOf(selectedSale.getTotalAmount()));
        JTextField itemsField = new JTextField(); // You can populate this with sale items

        Object[] message = {
            "Customer ID:", customerField,
            "Date:", dateField,
            "Total Amount:", totalAmountField,
            "Items:", itemsField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Sale", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            selectedSale.setCustomerId(Integer.parseInt(customerField.getText()));
            selectedSale.setSaleDate(java.sql.Timestamp.valueOf(dateField.getText()));
            selectedSale.setTotalAmount(Double.parseDouble(totalAmountField.getText()));

            String result = saleController.updateSale(selectedSale);
            JOptionPane.showMessageDialog(this, result);
            loadSales();
        }
    }

    private void deleteSale() {
        if (selectedSale == null) {
            JOptionPane.showMessageDialog(this, "Please select a sale to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this sale?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String result = saleController.deleteSale(selectedSale.getId());
                JOptionPane.showMessageDialog(this, result);
                loadSales();
                selectedSale = null;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting sale: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 