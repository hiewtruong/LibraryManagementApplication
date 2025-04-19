package com.uit.librarymanagementapplication.view.user;

import com.uit.librarymanagementapplication.controller.UserController;
import javax.swing.*;
import java.awt.*;

public class UserLoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserController controller;

    public UserLoginFrame() {
        controller = new UserController();
        setTitle("User Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel());
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> handleLogin());
        panel.add(loginBtn);

        add(panel);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

       
    }

}
