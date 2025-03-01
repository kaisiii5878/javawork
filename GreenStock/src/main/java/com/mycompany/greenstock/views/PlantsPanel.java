package com.mycompany.greenstock.views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import com.mycompany.greenstock.controllers.PlantController;
import com.mycompany.greenstock.database.models.Plant;
// ... existing imports ...

public class PlantsPanel extends JPanel {
    private final PlantController controller;
    private JPanel listPanel;
    private JPanel detailsPanel;
    private JTextField scientificNameField, commonNameField, plantTypeField, growthRateField, matureSizeField, sunReqField, waterReqField, soilTypeField, hardinessZoneField;
    private JTextArea descriptionArea;
    private Plant selectedPlant;

    public PlantsPanel(PlantController controller) {
        this.controller = controller;
        initUI();
        loadPlantsData();
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

        JLabel title = new JLabel("🌱 Plants Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        // Create a panel for CRUD buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));

        JButton addButton = new JButton("Add Plant");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addButton.setBackground(new Color(0, 100, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Plant");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        editButton.setBackground(new Color(0, 100, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        buttonPanel.add(editButton);

        JButton delButton = new JButton("Delete Plant");
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

        // Plant List Panel
        listPanel = createCardPanel("Plant List", new Color(144, 238, 144));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Wrap the list panel in a scroll pane
        JScrollPane listScrollPane = new JScrollPane(listPanel);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(listScrollPane);

        // Plant Details Panel
        detailsPanel = createCardPanel("Plant Details", new Color(152, 251, 152));
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(152, 251, 152));

        scientificNameField = new JTextField();
        commonNameField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        plantTypeField = new JTextField();
        growthRateField = new JTextField();
        matureSizeField = new JTextField();
        sunReqField = new JTextField();
        waterReqField = new JTextField();
        soilTypeField = new JTextField();
        hardinessZoneField = new JTextField();

        formPanel.add(new JLabel("Scientific Name:"));
        formPanel.add(scientificNameField);
        formPanel.add(new JLabel("Common Name:"));
        formPanel.add(commonNameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(new JLabel("Plant Type:"));
        formPanel.add(plantTypeField);
        formPanel.add(new JLabel("Growth Rate:"));
        formPanel.add(growthRateField);
        formPanel.add(new JLabel("Mature Size:"));
        formPanel.add(matureSizeField);
        formPanel.add(new JLabel("Sun Requirements:"));
        formPanel.add(sunReqField);
        formPanel.add(new JLabel("Water Requirements:"));
        formPanel.add(waterReqField);
        formPanel.add(new JLabel("Soil Type:"));
        formPanel.add(soilTypeField);
        formPanel.add(new JLabel("Hardiness Zone:"));
        formPanel.add(hardinessZoneField);

        detailsPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(detailsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        addButton.addActionListener(e -> addPlant());
        editButton.addActionListener(e -> editPlant());
        delButton.addActionListener(e -> deletePlant());
    }

    private void loadPlantsData() {
        listPanel.removeAll();
        List<Plant> plants = controller.getAllPlants();
        for (Plant plant : plants) {
            JPanel plantCard = createPlantCard(plant);
            listPanel.add(plantCard);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createPlantCard(Plant plant) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 0), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(300, 80)); // Set fixed size for the card

        JLabel nameLabel = new JLabel(plant.getScientificName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(nameLabel, BorderLayout.CENTER);

        JLabel commonNameLabel = new JLabel(plant.getCommonName());
        commonNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(commonNameLabel, BorderLayout.SOUTH);

        // Add mouse listener to the card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedPlant = plant;
                System.out.println("Selected Plant: " + plant.getScientificName()); // Debugging
                highlightSelectedCard(card);
                showPlantDetails(plant); // Show details for the selected plant
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

    private void showPlantDetails(Plant plant) {
        scientificNameField.setText(plant.getScientificName());
        commonNameField.setText(plant.getCommonName());
        descriptionArea.setText(plant.getDescription());
        plantTypeField.setText(plant.getPlantType());
        growthRateField.setText(plant.getGrowthRate());
        matureSizeField.setText(plant.getMatureSize());
        sunReqField.setText(plant.getSunRequirements());
        waterReqField.setText(plant.getWaterRequirements());
        soilTypeField.setText(plant.getSoilType());
        hardinessZoneField.setText(plant.getHardinessZone());
    }

    private void addPlant() {
        try {
            JTextField scientificNameField = new JTextField();
            JTextField commonNameField = new JTextField();
            JTextArea descriptionArea = new JTextArea(3, 20);
            
            // Create dropdown for plant type
            JComboBox<String> plantTypeComboBox = new JComboBox<>(new String[]{"TREE", "SHRUB", "FLOWER", "SUCCULENT", "HERB"});
            JComboBox<String> growthRateComboBox = new JComboBox<>(new String[]{"SLOW", "MODERATE", "FAST"});
            
            JTextField matureSizeField = new JTextField();
            JTextField sunReqField = new JTextField();
            JTextField waterReqField = new JTextField();
            JTextField soilTypeField = new JTextField();
            JTextField hardinessZoneField = new JTextField();

            Object[] message = {
                "Scientific Name:", scientificNameField,
                "Common Name:", commonNameField,
                "Description:", new JScrollPane(descriptionArea),
                "Plant Type:", plantTypeComboBox, // Use dropdown for plant type
                "Growth Rate:", growthRateComboBox, // Use dropdown for growth rate
                "Mature Size:", matureSizeField,
                "Sun Requirements:", sunReqField,
                "Water Requirements:", waterReqField,
                "Soil Type:", soilTypeField,
                "Hardiness Zone:", hardinessZoneField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Add New Plant", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Plant newPlant = new Plant();
                newPlant.setScientificName(scientificNameField.getText());
                newPlant.setCommonName(commonNameField.getText());
                newPlant.setDescription(descriptionArea.getText());
                newPlant.setPlantType((String) plantTypeComboBox.getSelectedItem()); // Get selected value from dropdown
                newPlant.setGrowthRate((String) growthRateComboBox.getSelectedItem()); // Get selected value from dropdown
                newPlant.setMatureSize(matureSizeField.getText());
                newPlant.setSunRequirements(sunReqField.getText());
                newPlant.setWaterRequirements(waterReqField.getText());
                newPlant.setSoilType(soilTypeField.getText());
                newPlant.setHardinessZone(hardinessZoneField.getText());

                String result = controller.createPlant(newPlant);
                JOptionPane.showMessageDialog(this, result);
                loadPlantsData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error in UI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void editPlant() {
        if (selectedPlant == null) {
            JOptionPane.showMessageDialog(this, "Please select a plant to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField scientificNameField = new JTextField(selectedPlant.getScientificName());
        JTextField commonNameField = new JTextField(selectedPlant.getCommonName());
        JTextArea descriptionArea = new JTextArea(selectedPlant.getDescription(), 3, 20);
        JComboBox<String> plantTypeComboBox = new JComboBox<>(new String[]{"TREE", "SHRUB", "FLOWER", "SUCCULENT", "HERB"});
        plantTypeComboBox.setSelectedItem(selectedPlant.getPlantType()); // Set selected value
        JComboBox<String> growthRateComboBox = new JComboBox<>(new String[]{"SLOW", "MODERATE", "FAST"});
        growthRateComboBox.setSelectedItem(selectedPlant.getGrowthRate()); // Set selected value
        JTextField matureSizeField = new JTextField(selectedPlant.getMatureSize());
        JTextField sunReqField = new JTextField(selectedPlant.getSunRequirements());
        JTextField waterReqField = new JTextField(selectedPlant.getWaterRequirements());
        JTextField soilTypeField = new JTextField(selectedPlant.getSoilType());
        JTextField hardinessZoneField = new JTextField(selectedPlant.getHardinessZone());

        Object[] message = {
            "Scientific Name:", scientificNameField,
            "Common Name:", commonNameField,
            "Description:", new JScrollPane(descriptionArea),
            "Plant Type:", plantTypeComboBox, // Use dropdown for plant type
            "Growth Rate:", growthRateComboBox, // Use dropdown for growth rate
            "Mature Size:", matureSizeField,
            "Sun Requirements:", sunReqField,
            "Water Requirements:", waterReqField,
            "Soil Type:", soilTypeField,
            "Hardiness Zone:", hardinessZoneField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Plant", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            selectedPlant.setScientificName(scientificNameField.getText());
            selectedPlant.setCommonName(commonNameField.getText());
            selectedPlant.setDescription(descriptionArea.getText());
            selectedPlant.setPlantType((String) plantTypeComboBox.getSelectedItem()); // Get selected value from dropdown
            selectedPlant.setGrowthRate((String) growthRateComboBox.getSelectedItem()); // Get selected value from dropdown
            selectedPlant.setMatureSize(matureSizeField.getText());
            selectedPlant.setSunRequirements(sunReqField.getText());
            selectedPlant.setWaterRequirements(waterReqField.getText());
            selectedPlant.setSoilType(soilTypeField.getText());
            selectedPlant.setHardinessZone(hardinessZoneField.getText());

            String result = controller.updatePlant(selectedPlant);
            JOptionPane.showMessageDialog(this, result);
            loadPlantsData();
        }
    }

    private void deletePlant() {
        if (selectedPlant == null) {
            JOptionPane.showMessageDialog(this, "Please select a plant to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this plant?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String result = controller.deletePlant(selectedPlant.getId());
                JOptionPane.showMessageDialog(this, result);
                loadPlantsData();
                selectedPlant = null;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting plant: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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