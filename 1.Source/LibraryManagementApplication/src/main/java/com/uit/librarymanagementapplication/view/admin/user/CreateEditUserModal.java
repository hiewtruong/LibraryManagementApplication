package com.uit.librarymanagementapplication.view.admin.user;

import com.uit.librarymanagementapplication.controller.UserController;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.lib.Constants.ConfirmConsts;
import com.uit.librarymanagementapplication.service.UserServices.IUserService;
import com.uit.librarymanagementapplication.service.UserServices.UserService;
import com.uit.librarymanagementapplication.view.lib.CommonUI;
import javax.swing.*;
import java.awt.*;

public class CreateEditUserModal extends JDialog {

    IUserService userService = UserService.getInstance();
    UserController userController = new UserController();
    private JPanel contentPanel;

    private JTextField txtFirstName, txtLastName, txtUserName, txtPassword, txtEmail, txtPhone, txtAddress;
    private JComboBox<String> cbGender;
    private JButton btnSave, btnCancel, btnDelete;

    public CreateEditUserModal(JFrame parent, JPanel contentPanel) {
        super(parent, "Create new User", true);
        initUI(null);
        setLocationRelativeTo(parent);
        this.contentPanel = contentPanel;
    }

    public CreateEditUserModal(JFrame parent, JPanel contentPanel, UserRoleDTO user) {
        super(parent, "Edit User - " + user.getUserName(), true);
        initUI(user);
        setLocationRelativeTo(parent);
        this.contentPanel = contentPanel;
    }

    private void initUI(UserRoleDTO user) {
        setSize(500, 400); // Tăng chiều cao để chứa thêm trường Password
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // First Name
        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setPreferredSize(new Dimension(100, 25));
        txtFirstName = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblFirstName, gbc);
        gbc.gridx = 1;
        formPanel.add(txtFirstName, gbc);

        // Last Name
        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setPreferredSize(new Dimension(100, 25));
        txtLastName = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblLastName, gbc);
        gbc.gridx = 1;
        formPanel.add(txtLastName, gbc);

        // User Name
        JLabel lblUserName = new JLabel("User Name:");
        lblUserName.setPreferredSize(new Dimension(100, 25));
        txtUserName = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblUserName, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUserName, gbc);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setPreferredSize(new Dimension(100, 25));
        txtPassword = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPassword, gbc);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setPreferredSize(new Dimension(100, 25));
        txtEmail = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblEmail, gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);

        // Phone
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setPreferredSize(new Dimension(100, 25));
        txtPhone = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblPhone, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPhone, gbc);

        // Address
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setPreferredSize(new Dimension(100, 25));
        txtAddress = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(lblAddress, gbc);
        gbc.gridx = 1;
        formPanel.add(txtAddress, gbc);

        // Gender (ComboBox with Male/Female)
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setPreferredSize(new Dimension(100, 25));
        String[] genders = {"Female", "Male"};
        cbGender = new JComboBox<>(genders);
        cbGender.setPreferredSize(new Dimension(220, 25));
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(lblGender, gbc);
        gbc.gridx = 1;
        formPanel.add(cbGender, gbc);

        // Add empty row for spacing
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        formPanel.add(new JLabel(), gbc);

        // Footer panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Delete button on the left
        if (user != null) {
            btnDelete = new JButton("Delete");
            btnDelete.setForeground(Color.RED);
            btnDelete.setFocusPainted(false);
            btnDelete.setOpaque(true);
            btnDelete.setContentAreaFilled(true);
            btnDelete.addActionListener(e -> {
                int response = CommonUI.showConfirmDialog(this, ConfirmConsts.CONFIRM_DELETE_CONTENT, ConfirmConsts.CONFIRM_TITLE);
                if (response == JOptionPane.YES_OPTION) {
                    userService.deleteUser(user.getUserID());
                    CommonUI.showSuccess(this, "User deleted successfully");
                    dispose();
                }
            });
            buttonPanel.add(btnDelete, BorderLayout.WEST);
        }

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        btnCancel.setForeground(Color.RED);
        btnCancel.setFocusPainted(false);
        btnCancel.setOpaque(true);
        btnCancel.setContentAreaFilled(true);
        rightButtonPanel.add(btnSave);
        rightButtonPanel.add(btnCancel);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        footerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // Fill data if in edit mode
        if (user != null) {
            txtFirstName.setText(user.getFirstName() != null ? user.getFirstName() : "");
            txtLastName.setText(user.getLastName() != null ? user.getLastName() : "");
            txtUserName.setText(user.getUserName() != null ? user.getUserName() : "");
            txtPassword.setText(user.getPassword() != null ? user.getPassword() : "");
            txtEmail.setText(user.getEmail() != null ? user.getEmail() : "");
            txtPhone.setText(user.getPhone() != null ? user.getPhone() : "");
            txtAddress.setText(user.getAddress() != null ? user.getAddress() : "");
            cbGender.setSelectedItem(user.getGender() == 1 ? "Male" : "Female");
        }

        // Save action
        btnSave.addActionListener(e -> {
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String userName = txtUserName.getText().trim();
            String password = txtPassword.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String address = txtAddress.getText().trim();
            int gender = cbGender.getSelectedItem().equals("Male") ? 1 : 0;

            // Kiểm tra các trường bắt buộc
            if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || email.isEmpty()) {
                CommonUI.showAlerValidate(this, "First Name, Last Name, User Name, and Email cannot be empty");
                return;
            }

            // Kiểm tra mật khẩu trong chế độ tạo mới
            if (user == null && password.isEmpty()) {
                CommonUI.showAlerValidate(this, "Password cannot be empty when creating a new user");
                return;
            }

            UserRoleDTO userDto = new UserRoleDTO();
            userDto.setUserID(user != null ? user.getUserID() : 0);
            userDto.setFirstName(firstName);
            userDto.setLastName(lastName);
            userDto.setUserName(userName);
            // Xử lý mật khẩu
            if (user == null || !password.isEmpty()) {
                // Trong chế độ tạo mới hoặc nếu mật khẩu không trống trong chế độ chỉnh sửa
                userDto.setPassword(password);
            } else {
                // Trong chế độ chỉnh sửa, nếu mật khẩu trống, giữ nguyên mật khẩu cũ
                userDto.setPassword(user.getPassword());
            }
            userDto.setEmail(email);
            userDto.setPhone(phone);
            userDto.setAddress(address);
            userDto.setGender(gender);
            userDto.setIsDelete(0);
            userDto.setIsAdmin(0);

            if (user == null) {
                userService.createUser(userDto);
            } else {
                userService.updateUser(userDto);
            }

            CommonUI.showSuccess(this, user == null ? "Create user successful" : "Update user successful");
            dispose();
            userController.Init(contentPanel, true); // Sửa "Init" thành "init" để đúng cú pháp
        });

        btnCancel.addActionListener(e -> dispose());
    }
}