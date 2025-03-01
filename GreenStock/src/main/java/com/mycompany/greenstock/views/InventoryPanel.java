package com.mycompany.greenstock.views;

import javax.swing.*;
import java.awt.*;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import com.mycompany.greenstock.controllers.InventoryController;
import com.mycompany.greenstock.database.models.Inventory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class InventoryPanel extends JPanel {
    private final InventoryController controller;
    private JPanel listPanel;
    private JPanel detailsPanel;
    private JTextField plantField, nurseryField, quantityField, potSizeField, priceField;
    private Inventory selectedInventory;

    public InventoryPanel(InventoryController controller) {
        this.controller = controller;
        initUI();
        loadInventory();
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

        JLabel title = new JLabel("📦 Inventory Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        // Create a panel for CRUD buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));

        JButton addButton = new JButton("Add Item");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addButton.setBackground(new Color(0, 100, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Item");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        editButton.setBackground(new Color(0, 100, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        buttonPanel.add(editButton);

        JButton delButton = new JButton("Delete Item");
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

        // Inventory List Panel
        listPanel = createCardPanel("Inventory List", new Color(144, 238, 144));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Wrap the list panel in a scroll pane
        JScrollPane listScrollPane = new JScrollPane(listPanel);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(listScrollPane);

        // Inventory Details Panel
        detailsPanel = createCardPanel("Inventory Details", new Color(152, 251, 152));
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(152, 251, 152));

        plantField = new JTextField();
        nurseryField = new JTextField();
        quantityField = new JTextField();
        potSizeField = new JTextField();
        priceField = new JTextField();

        formPanel.add(new JLabel("Plant:"));
        formPanel.add(plantField);
        formPanel.add(new JLabel("Nursery:"));
        formPanel.add(nurseryField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Pot Size:"));
        formPanel.add(potSizeField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);

        detailsPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(detailsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        addButton.addActionListener(e -> addInventory());
        editButton.addActionListener(e -> editInventory());
        delButton.addActionListener(e -> deleteInventory());
    }

    private void loadInventory() {
        listPanel.removeAll();
        List<Inventory> inventoryList = controller.getAllInventory();
        for (Inventory inventory : inventoryList) {
            JPanel inventoryCard = createInventoryCard(inventory);
            listPanel.add(inventoryCard);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createInventoryCard(Inventory inventory) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 0), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(300, 80)); // Set fixed size for the card

        JLabel plantLabel = new JLabel("Plant ID: " + inventory.getPlantId());
        plantLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(plantLabel, BorderLayout.CENTER);

        JLabel nurseryLabel = new JLabel("Nursery ID: " + inventory.getNurseryId());
        nurseryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(nurseryLabel, BorderLayout.SOUTH);

        // Add mouse listener to the card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedInventory = inventory;
                System.out.println("Selected Inventory: " + inventory.getId()); // Debugging
                highlightSelectedCard(card);
                showInventoryDetails(inventory); // Show details for the selected inventory
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

    private void showInventoryDetails(Inventory inventory) {
        plantField.setText(String.valueOf(inventory.getPlantId()));
        nurseryField.setText(String.valueOf(inventory.getNurseryId()));
        quantityField.setText(String.valueOf(inventory.getQuantity()));
        potSizeField.setText(inventory.getPotSize());
        priceField.setText(String.valueOf(inventory.getPrice()));
    }

    private void addInventory() {
        try {
            JTextField plantField = new JTextField();
            JTextField nurseryField = new JTextField();
            JTextField quantityField = new JTextField();
            JTextField potSizeField = new JTextField();
            JTextField priceField = new JTextField();

            Object[] message = {
                "Plant ID:", plantField,
                "Nursery ID:", nurseryField,
                "Quantity:", quantityField,
                "Pot Size:", potSizeField,
                "Price:", priceField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Add New Inventory", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Inventory newInventory = new Inventory();
                newInventory.setPlantId(Integer.parseInt(plantField.getText()));
                newInventory.setNurseryId(Integer.parseInt(nurseryField.getText()));
                newInventory.setQuantity(Integer.parseInt(quantityField.getText()));
                newInventory.setPotSize(potSizeField.getText());
                newInventory.setPrice(Double.parseDouble(priceField.getText()));

                String result = controller.createInventory(newInventory);
                JOptionPane.showMessageDialog(this, result);
                loadInventory();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding inventory: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editInventory() {
        if (selectedInventory == null) {
            JOptionPane.showMessageDialog(this, "Please select an inventory to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField plantField = new JTextField(String.valueOf(selectedInventory.getPlantId()));
        JTextField nurseryField = new JTextField(String.valueOf(selectedInventory.getNurseryId()));
        JTextField quantityField = new JTextField(String.valueOf(selectedInventory.getQuantity()));
        JTextField potSizeField = new JTextField(selectedInventory.getPotSize());
        JTextField priceField = new JTextField(String.valueOf(selectedInventory.getPrice()));

        Object[] message = {
            "Plant ID:", plantField,
            "Nursery ID:", nurseryField,
            "Quantity:", quantityField,
            "Pot Size:", potSizeField,
            "Price:", priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Inventory", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            selectedInventory.setPlantId(Integer.parseInt(plantField.getText()));
            selectedInventory.setNurseryId(Integer.parseInt(nurseryField.getText()));
            selectedInventory.setQuantity(Integer.parseInt(quantityField.getText()));
            selectedInventory.setPotSize(potSizeField.getText());
            selectedInventory.setPrice(Double.parseDouble(priceField.getText()));

            String result = controller.updateInventory(selectedInventory);
            JOptionPane.showMessageDialog(this, result);
            loadInventory();
        }
    }

    private void deleteInventory() {
        if (selectedInventory == null) {
            JOptionPane.showMessageDialog(this, "Please select an inventory to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this inventory?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String result = controller.deleteInventory(selectedInventory.getId());
                JOptionPane.showMessageDialog(this, result);
                loadInventory();
                selectedInventory = null;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting inventory: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 