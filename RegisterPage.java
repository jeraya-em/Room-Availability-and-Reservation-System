/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package activity;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegisterPage extends BaseFrame {

    private JComboBox<String> buildingBox;
    private JComboBox<String> roomBox;
    private JComboBox<String> dayBox;
    private JTextField startField;
    private JTextField endField;
    private JTextField subjectField;
    private JTextField instructorField;

    private static final DateTimeFormatter TIME_FORMAT =
        DateTimeFormatter.ofPattern("h:mm a");

    public RegisterPage() {
        setTitle("Register Reservation");
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel(
            "REGISTER ROOM RESERVATION",
            SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setForeground(WHITE);
        heading.setBorder(
            BorderFactory.createEmptyBorder(20, 10, 10, 10)
        );

        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setBackground(DARK);
        headingPanel.add(heading, BorderLayout.CENTER);

        add(headingPanel, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(DARK);
        form.setBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        buildingBox = new JComboBox<>(new String[] {
            ReservationStore.KALAP_BUILDING,
            ReservationStore.MAIN_BUILDING
        });

        roomBox = new JComboBox<>();
        dayBox = new JComboBox<>(new String[] {
            "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"
        });

        startField = new JTextField("7:00 AM", 15);
        endField = new JTextField("8:00 AM", 15);
        subjectField = new JTextField(15);
        instructorField = new JTextField(15);

        addFormRow(form, gbc, 0, "Building:", buildingBox);
        addFormRow(form, gbc, 1, "Room:", roomBox);
        addFormRow(form, gbc, 2, "Day:", dayBox);
        addFormRow(form, gbc, 3, "Start Time:", startField);
        addFormRow(form, gbc, 4, "End Time:", endField);
        addFormRow(form, gbc, 5, "Subject / Course:", subjectField);
        addFormRow(form, gbc, 6, "Instructor:", instructorField);

        buildingBox.addActionListener(e -> updateRooms());
        updateRooms();

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(DARK);
        center.add(form);

        add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 12)
        );
        buttons.setBackground(DARK);

        JButton reserveButton = makeButton("Reserve");
        JButton clearButton = makeButton("Clear");
        JButton homeButton = makeButton("Home");

        buttons.add(reserveButton);
        buttons.add(clearButton);
        buttons.add(homeButton);

        add(buttons, BorderLayout.SOUTH);

        reserveButton.addActionListener(e -> saveReservation());

        clearButton.addActionListener(e -> clearForm());

        homeButton.addActionListener(e -> {
            HomePage page = new HomePage();
            page.setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent input) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.35;

        JLabel label = makeLabel(labelText);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;

        input.setPreferredSize(new Dimension(240, 35));
        panel.add(input, gbc);
    }

    private void updateRooms() {
        String building = (String) buildingBox.getSelectedItem();

        roomBox.removeAllItems();

        if (building == null) {
            return;
        }

        for (String room : ReservationStore.getRooms(building)) {
            roomBox.addItem(room);
        }
    }

    private void saveReservation() {
        try {
            String building = (String) buildingBox.getSelectedItem();
            String room = (String) roomBox.getSelectedItem();
            String day = (String) dayBox.getSelectedItem();

            if (building == null || room == null || day == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select a building, room, and day."
                );
                return;
            }

            LocalTime start =
                LocalTime.parse(startField.getText().trim().toUpperCase(),
                                TIME_FORMAT);

            LocalTime end =
                LocalTime.parse(endField.getText().trim().toUpperCase(),
                                TIME_FORMAT);

            RoomReservation reservation = new RoomReservation(
                building,
                room,
                day,
                start,
                end,
                subjectField.getText().trim(),
                instructorField.getText().trim()
            );

            String result =
                ReservationStore.addReservation(reservation);

            if ("SUCCESS".equals(result)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Reservation saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                BuildingPage page = new BuildingPage(building);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Reservation Error",
                    JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Enter time in this format: 7:00 AM or 2:30 PM.",
                "Invalid Time",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (RuntimeException ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Could not save the reservation: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {
        startField.setText("");
        endField.setText("");
        subjectField.setText("");
        instructorField.setText("");
        buildingBox.setSelectedIndex(0);
        dayBox.setSelectedIndex(0);
        updateRooms();
    }
}
