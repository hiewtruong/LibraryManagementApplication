package com.uit.librarymanagementapplication.controller;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.lib.ApiException;
import com.uit.librarymanagementapplication.service.UserServices.IUserService;
import com.uit.librarymanagementapplication.service.UserServices.UserService;
import com.uit.librarymanagementapplication.view.admin.AdminDashboardFrame;
import com.uit.librarymanagementapplication.view.admin.user.CreateEditUserModal;
import com.uit.librarymanagementapplication.view.admin.user.UserPanel;
import com.uit.librarymanagementapplication.view.lib.CommonUI;
import com.uit.librarymanagementapplication.view.user.BookListForm;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UserController {

    IUserService userService = UserService.getInstance();
    private static UserDTO currentUser = null;

    public UserController() {

    }

    public void Login(JFrame currentFrame, String userName, String password, boolean isAdmin) {
        try {
            UserDTO user = userService.login(userName, password, isAdmin);
            currentFrame.dispose();
            if (isAdmin) {
                new AdminDashboardFrame(user);
            } else {
                new BookListForm();
            }
        } catch (ApiException e) {
            CommonUI.showErrorApi(currentFrame, e);
        }
    }

    public UserDTO handleLogin(String username, String password) {
        UserDTO user = userService.loginUser(username, password);
        if (user != null) {
            currentUser = user;
            return user;

        }
        return null;
    }

    public UserDTO getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public void Init(JPanel contentPanel, boolean forceReload) {
        if (forceReload || !(contentPanel.getComponentCount() > 0 && contentPanel.getComponent(0) instanceof UserPanel)) {
            contentPanel.removeAll();
            List<UserRoleDTO> result = userService.getAllUsersByKeyword("", "");
            UserPanel userPanel = new UserPanel(result, contentPanel);
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(userPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public void CreateUser(JFrame parentFrame, JPanel contentPanel) {
        CreateEditUserModal dialog = new CreateEditUserModal(parentFrame, contentPanel);
        dialog.setVisible(true);
    }

    public void UpdateUser(JFrame parentFrame, JPanel contentPanel, UserRoleDTO user) {
        CreateEditUserModal dialog = new CreateEditUserModal(parentFrame, contentPanel, user);
        dialog.setVisible(true);

    }
}
