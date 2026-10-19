/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package activity;

import javax.swing.*;
import java.awt.*;

public class HomePage extends BaseFrame {

    public HomePage() {
        setTitle("Room Availability and Reservation System");
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BLUE);
        header.setBorder(
            BorderFactory.createEmptyBorder(22, 10, 22, 10)
        );

        JLabel title = new JLabel("ROOM RESERVATION SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
            "Room Availability and Reservation System"
        );
        subtitle.setForeground(WHITE);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(DARK);
        menu.setBorder(
            BorderFactory.createEmptyBorder(25, 15, 25, 15)
        );

        JLabel welcome = new JLabel("Welcome!");
        welcome.setFont(new Font("Arial", Font.BOLD, 23));
        welcome.setForeground(WHITE);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instruction = new JLabel("Choose an option below");
        instruction.setForeground(WHITE);
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton kalapButton = makeButton("Kalap Building");
        JButton mainButton = makeButton("Main Building");
        JButton registerButton = makeButton("Register Reservation");
        JButton exitButton = makeButton("Exit");

        JButton[] buttons = {
            kalapButton, mainButton, registerButton, exitButton
        };

        for (JButton button : buttons) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(230, 42));
            button.setPreferredSize(new Dimension(230, 42));
        }

        menu.add(welcome);
        menu.add(Box.createVerticalStrut(8));
        menu.add(instruction);
        menu.add(Box.createVerticalStrut(25));
        menu.add(kalapButton);
        menu.add(Box.createVerticalStrut(12));
        menu.add(mainButton);
        menu.add(Box.createVerticalStrut(12));
        menu.add(registerButton);
        menu.add(Box.createVerticalStrut(12));
        menu.add(exitButton);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(DARK);
        center.add(menu);

        add(center, BorderLayout.CENTER);

        // Button actions
        kalapButton.addActionListener(e -> openBuilding(
            ReservationStore.KALAP_BUILDING
        ));

        mainButton.addActionListener(e -> openBuilding(
            ReservationStore.MAIN_BUILDING
        ));

        registerButton.addActionListener(e -> {
            try {
                RegisterPage page = new RegisterPage();
                page.setVisible(true);
                dispose();
            } catch (RuntimeException ex) {
                showError("Could not open Register Page.", ex);
            }
        });

        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
    }

    private void openBuilding(String building) {
        try {
            BuildingPage page = new BuildingPage(building);
            page.setVisible(true);
            dispose();
        } catch (RuntimeException ex) {
            showError("Could not open " + building + ".", ex);
        }
    }

    private void showError(String message, Exception ex) {
        ex.printStackTrace();

        JOptionPane.showMessageDialog(
            this,
            message + "\nDetails: " + ex.getMessage(),
            "Navigation Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
