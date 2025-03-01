package com.mycompany.greenstock.views;

import javax.swing.*;
import java.awt.*;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import com.mycompany.greenstock.controllers.CustomerController;
import com.mycompany.greenstock.database.models.Customer;

public class CustomersPanel extends JPanel {
    private final CustomerController controller;
    private JPanel listPanel;
    private JPanel detailsPanel;
    private JTextField nameField, emailField, phoneField, addressField;
    private Customer selectedCustomer;

    public CustomersPanel(CustomerController controller) {
        this.controller = controller;
        initUI();
        loadCustomers();
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

        JLabel title = new JLabel("👥 Customers Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        // Create a panel for CRUD buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));

        JButton addButton = new JButton("Add Customer");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addButton.setBackground(new Color(0, 100, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Customer");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        editButton.setBackground(new Color(0, 100, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        buttonPanel.add(editButton);

        JButton delButton = new JButton("Delete Customer");
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

        // Customer List Panel
        listPanel = createCardPanel("Customer List", new Color(144, 238, 144));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Wrap the list panel in a scroll pane
        JScrollPane listScrollPane = new JScrollPane(listPanel);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(listScrollPane);

        // Customer Details Panel
        detailsPanel = createCardPanel("Customer Details", new Color(152, 251, 152));
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(152, 251, 152));

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        detailsPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(detailsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        addButton.addActionListener(e -> addCustomer());
        editButton.addActionListener(e -> editCustomer());
        delButton.addActionListener(e -> deleteCustomer());
    }

    private void loadCustomers() {
        listPanel.removeAll();
        List<Customer> customers = controller.getAllCustomers();
        for (Customer customer : customers) {
            JPanel customerCard = createCustomerCard(customer);
            listPanel.add(customerCard);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createCustomerCard(Customer customer) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 0), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(300, 80)); // Set fixed size for the card

        JLabel nameLabel = new JLabel(customer.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(nameLabel, BorderLayout.CENTER);

        JLabel emailLabel = new JLabel(customer.getEmail());
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(emailLabel, BorderLayout.SOUTH);

        // Add mouse listener to the card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedCustomer = customer;
                System.out.println("Selected Customer: " + customer.getName()); // Debugging
                highlightSelectedCard(card);
                showCustomerDetails(customer); // Show details for the selected customer
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

    private void showCustomerDetails(Customer customer) {
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        phoneField.setText(customer.getPhone());
        addressField.setText(customer.getAddress());
    }

    private void addCustomer() {
        try {
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField phoneField = new JTextField();
            JTextField addressField = new JTextField();

            Object[] message = {
                "Name:", nameField,
                "Email:", emailField,
                "Phone:", phoneField,
                "Address:", addressField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Add New Customer", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Customer newCustomer = new Customer();
                newCustomer.setName(nameField.getText());
                newCustomer.setEmail(emailField.getText());
                newCustomer.setPhone(phoneField.getText());
                newCustomer.setAddress(addressField.getText());

                String result = controller.createCustomer(newCustomer);
                JOptionPane.showMessageDialog(this, result);
                loadCustomers();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editCustomer() {
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(selectedCustomer.getName());
        JTextField emailField = new JTextField(selectedCustomer.getEmail());
        JTextField phoneField = new JTextField(selectedCustomer.getPhone());
        JTextField addressField = new JTextField(selectedCustomer.getAddress());

        Object[] message = {
            "Name:", nameField,
            "Email:", emailField,
            "Phone:", phoneField,
            "Address:", addressField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            selectedCustomer.setName(nameField.getText());
            selectedCustomer.setEmail(emailField.getText());
            selectedCustomer.setPhone(phoneField.getText());
            selectedCustomer.setAddress(addressField.getText());

            String result = controller.updateCustomer(selectedCustomer);
            JOptionPane.showMessageDialog(this, result);
            loadCustomers();
        }
    }

    private void deleteCustomer() {
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this customer?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String result = controller.deleteCustomer(selectedCustomer.getId());
                JOptionPane.showMessageDialog(this, result);
                loadCustomers();
                selectedCustomer = null;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 