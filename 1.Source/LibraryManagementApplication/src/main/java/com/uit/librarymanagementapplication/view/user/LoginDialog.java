package com.uit.librarymanagementapplication.view.user;

import com.uit.librarymanagementapplication.controller.UserController;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.view.lib.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserController controller;
    private boolean loginSuccessful = false;

    public LoginDialog(JFrame parent) {
        super(parent, "Đăng nhập", true);
        controller = new UserController();

        setSize(350, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Tên đăng nhập:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton cancelBtn = new JButton("Hủy");
        cancelBtn.addActionListener(e -> dispose());
        panel.add(cancelBtn);

        JButton loginBtn = new JButton("Đăng nhập");
        loginBtn.addActionListener(e -> handleLogin());
        panel.add(loginBtn);

        add(panel);

        passwordField.addActionListener(e -> handleLogin());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                loginSuccessful = false;
            }
        });
    }

    private UserDTO loggedInUser;

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            CommonUI.showAlerValidate(this, "Vui lòng điền đầy đủ thông tin");
            return;
        }

        UserDTO user = controller.handleLogin(username, password);

        if (user != null) {
            loggedInUser = user;
            loginSuccessful = true;
            dispose();
            CommonUI.showSuccess(this, "Đăng nhập thành công");
            dispose();
        } else {
            CommonUI.showError(this, "Sai tài khoản hoặc mật khẩu");
        }
    }

    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
