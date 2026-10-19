/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package activity;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {

    public static final Color DARK = new Color(37, 37, 37);
    public static final Color PANEL = new Color(48, 48, 48);
    public static final Color BLUE = new Color(23, 97, 130);
    public static final Color WHITE = Color.WHITE;

    public BaseFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(DARK);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        int width = Math.min(1000, screen.width - 60);
        int height = Math.min(720, screen.height - 80);

        width = Math.max(400, width);
        height = Math.max(350, height);

        setSize(width, height);
        setMinimumSize(new Dimension(400, 350));
        setLocationRelativeTo(null);
    }

    public JButton makeButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(WHITE);
        button.setBackground(BLUE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(170, 40));

        return button;
    }

    public JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    public void stylePanel(JPanel panel) {
        panel.setBackground(DARK);
    }
}
