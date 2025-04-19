package com.uit.librarymanagementapplication.view.admin;

import com.uit.librarymanagementapplication.controller.UserController;
import com.uit.librarymanagementapplication.lib.ApiException;
import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserController controller;

    public AdminLoginFrame() {
        controller = new UserController();
        setTitle("Admin Login");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        // Tạo split pane chia đôi màn hình
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setDividerSize(0); // Không cho resize

        // Panel bên trái: ảnh nền
        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("login_image.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        splitPane.setLeftComponent(imageLabel);

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

        JLabel titleLabel = new JLabel("Welcome to Library Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.BLACK);

        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createTitledBorder("Tài Khoản"));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createTitledBorder("Mật Khẩu"));

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBackground(new Color(0, 102, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(true);
        loginButton.setOpaque(true);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginButton.addActionListener(e -> handleLogin());

        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loginPanel.add(usernameField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        loginPanel.add(loginButton);

        splitPane.setRightComponent(loginPanel);
        add(splitPane);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        controller.Login(this, username, password, true);
    }
}
