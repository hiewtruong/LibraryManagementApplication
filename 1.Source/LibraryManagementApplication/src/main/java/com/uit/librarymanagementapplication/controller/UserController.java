package com.uit.librarymanagementapplication.controller;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.Message;
import com.uit.librarymanagementapplication.lib.ApiException;
import com.uit.librarymanagementapplication.service.UserServices.IUserService;
import com.uit.librarymanagementapplication.service.UserServices.UserService;
import com.uit.librarymanagementapplication.view.admin.AdminDashboardFrame;
import com.uit.librarymanagementapplication.view.lib.CommonUI;
import com.uit.librarymanagementapplication.view.user.BookListForm;
import javax.swing.JFrame;

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
}
