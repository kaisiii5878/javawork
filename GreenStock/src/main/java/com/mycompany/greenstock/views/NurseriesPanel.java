package com.mycompany.greenstock.views;

import javax.swing.*;
import java.awt.*;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import com.mycompany.greenstock.controllers.NurseryController;
import com.mycompany.greenstock.database.models.Nursery;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class NurseriesPanel extends JPanel {
    private final NurseryController controller;
    private JPanel listPanel;
    private JPanel detailsPanel;
    private JTextField nameField, locationField, contactField, emailField, websiteField;
    private Nursery selectedNursery;

    public NurseriesPanel(NurseryController controller) {
        this.controller = controller;
        initUI();
        loadNurseries();
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

        JLabel title = new JLabel("🏡 Nurseries Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        // Create a panel for CRUD buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));

        JButton addButton = new JButton("Add Nursery");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addButton.setBackground(new Color(0, 100, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Nursery");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        editButton.setBackground(new Color(0, 100, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        buttonPanel.add(editButton);

        JButton delButton = new JButton("Delete Nursery");
        delButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        delButton.setBackground(new Color(0, 100, 0));
        delButton.setForeground(Color.WHITE);
        delButton.setFocusPainted(false);
        buttonPanel.add(delButton);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Add action listeners to buttons
        addButton.addActionListener(e -> showAddNurseryPopup());
        editButton.addActionListener(e -> showEditNurseryPopup());
        delButton.addActionListener(e -> deleteNursery());

        // Main Content Panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 250, 240));

        // Nursery List Panel
        listPanel = createCardPanel("Nursery List", new Color(144, 238, 144));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Wrap the list panel in a scroll pane
        JScrollPane listScrollPane = new JScrollPane(listPanel);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(listScrollPane);

        // Nursery Details Panel
        detailsPanel = createCardPanel("Nursery Details", new Color(152, 251, 152));
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(152, 251, 152));

        nameField = new JTextField();
        locationField = new JTextField();
        contactField = new JTextField();
        emailField = new JTextField();
        websiteField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Location:"));
        formPanel.add(locationField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Website:"));
        formPanel.add(websiteField);

        detailsPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(detailsPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void showAddNurseryPopup() {
        JTextField nameField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField websiteField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Location:", locationField,
            "Contact:", contactField,
            "Email:", emailField,
            "Website:", websiteField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Nursery", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Nursery newNursery = new Nursery();
                newNursery.setName(nameField.getText());
                newNursery.setLocation(locationField.getText());
                newNursery.setContactPhone(contactField.getText());
                newNursery.setEmail(emailField.getText());
                newNursery.setWebsite(websiteField.getText());

                String result = controller.createNursery(newNursery);
                JOptionPane.showMessageDialog(this, result);
                loadNurseries();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding nursery: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditNurseryPopup() {
        if (selectedNursery == null) {
            JOptionPane.showMessageDialog(this, "Please select a nursery to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(selectedNursery.getName());
        JTextField locationField = new JTextField(selectedNursery.getLocation());
        JTextField contactField = new JTextField(selectedNursery.getContactPhone());
        JTextField emailField = new JTextField(selectedNursery.getEmail());
        JTextField websiteField = new JTextField(selectedNursery.getWebsite());

        Object[] message = {
            "Name:", nameField,
            "Location:", locationField,
            "Contact:", contactField,
            "Email:", emailField,
            "Website:", websiteField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Nursery", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                selectedNursery.setName(nameField.getText());
                selectedNursery.setLocation(locationField.getText());
                selectedNursery.setContactPhone(contactField.getText());
                selectedNursery.setEmail(emailField.getText());
                selectedNursery.setWebsite(websiteField.getText());

                String result = controller.updateNursery(selectedNursery);
                JOptionPane.showMessageDialog(this, result);
                loadNurseries();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating nursery: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteNursery() {
        if (selectedNursery == null) {
            JOptionPane.showMessageDialog(this, "Please select a nursery to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this nursery?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String result = controller.deleteNursery(selectedNursery.getId());
                JOptionPane.showMessageDialog(this, result);
                loadNurseries();
                selectedNursery = null;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting nursery: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadNurseries() {
        listPanel.removeAll();
        List<Nursery> nurseries = controller.getAllNurseries();
        for (Nursery nursery : nurseries) {
            JPanel nurseryCard = createNurseryCard(nursery);
            listPanel.add(nurseryCard);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createNurseryCard(Nursery nursery) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 0), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(300, 80)); // Set fixed size for the card

        JLabel nameLabel = new JLabel(nursery.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(nameLabel, BorderLayout.CENTER);

        JLabel locationLabel = new JLabel(nursery.getLocation());
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(locationLabel, BorderLayout.SOUTH);

        // Add mouse listener to the card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedNursery = nursery;
                System.out.println("Selected Nursery: " + nursery.getName()); // Debugging
                highlightSelectedCard(card);
                showNurseryDetails(nursery); // Show details for the selected nursery
            }
        });

        return card;
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

    private void showNurseryDetails(Nursery nursery) {
        nameField.setText(nursery.getName());
        locationField.setText(nursery.getLocation());
        contactField.setText(nursery.getContactPhone());
        emailField.setText(nursery.getEmail());
        websiteField.setText(nursery.getWebsite());
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
} 