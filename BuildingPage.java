/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package activity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BuildingPage extends BaseFrame {

    private final String building;
    private JComboBox<String> dayBox;
    private DefaultTableModel model;

    public BuildingPage(String building) {
        this.building = building;

        setTitle(building + " - Room Availability");
        setLayout(new BorderLayout(8, 8));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(DARK);
        top.setBorder(
            BorderFactory.createEmptyBorder(15, 10, 5, 10)
        );

        JLabel heading = new JLabel(building);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(WHITE);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Room Availability Schedule");
        subtitle.setForeground(WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel controls = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 8, 10)
        );
        controls.setBackground(DARK);

        controls.add(makeLabel("Select Day:"));

        dayBox = new JComboBox<>(new String[] {
            "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"
        });
        dayBox.setPreferredSize(new Dimension(140, 35));
        controls.add(dayBox);

        JButton refreshButton = makeButton("Refresh");
        JButton registerButton = makeButton("Register");
        JButton homeButton = makeButton("Home");

        controls.add(refreshButton);
        controls.add(registerButton);
        controls.add(homeButton);

        top.add(heading);
        top.add(Box.createVerticalStrut(5));
        top.add(subtitle);
        top.add(controls);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(
            new String[] {
                "Room", "Day", "Status", "Time",
                "Subject / Course", "Instructor"
            },
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setBackground(PANEL);
        table.setForeground(WHITE);
        table.setGridColor(Color.GRAY);
        table.setSelectionBackground(BLUE);
        table.setSelectionForeground(WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 13)
        );
        table.getTableHeader().setBackground(BLUE);
        table.getTableHeader().setForeground(WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(PANEL);
        scroll.setBorder(
            BorderFactory.createEmptyBorder(0, 12, 10, 12)
        );

        add(scroll, BorderLayout.CENTER);

        dayBox.addActionListener(e -> loadSchedule());
        refreshButton.addActionListener(e -> loadSchedule());

        registerButton.addActionListener(e -> {
            RegisterPage page = new RegisterPage();
            page.setVisible(true);
            dispose();
        });

        homeButton.addActionListener(e -> {
            HomePage page = new HomePage();
            page.setVisible(true);
            dispose();
        });

        loadSchedule();
        setLocationRelativeTo(null);
    }

    private void loadSchedule() {
        model.setRowCount(0);

        String day = (String) dayBox.getSelectedItem();

        if (day == null) {
            return;
        }

        ArrayList<String> rooms =
            ReservationStore.getRooms(building);

        ArrayList<RoomReservation> bookings =
            ReservationStore.getReservations(building, day);

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("h:mm a");

        for (String room : rooms) {
            boolean found = false;

            for (RoomReservation booking : bookings) {
                if (booking.getRoom().equals(room)) {
                    found = true;

                    String time =
                        booking.getStartTime().format(formatter)
                        + " - "
                        + booking.getEndTime().format(formatter);

                    model.addRow(new Object[] {
                        room,
                        day,
                        "Reserved",
                        time,
                        booking.getSubject(),
                        booking.getInstructor()
                    });
                }
            }

            if (!found) {
                model.addRow(new Object[] {
                    room, day, "Available", "-", "-", "-"
                });
            }
        }
    }
}
