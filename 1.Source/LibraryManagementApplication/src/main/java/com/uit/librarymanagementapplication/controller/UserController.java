package com.uit.librarymanagementapplication.controller;

import com.uit.librarymanagementapplication.domain.DTO.UserDTO;
import com.uit.librarymanagementapplication.domain.model.Message;
import com.uit.librarymanagementapplication.lib.ApiException;
import com.uit.librarymanagementapplication.service.IUserService;
import com.uit.librarymanagementapplication.service.UserService;
import com.uit.librarymanagementapplication.view.admin.AdminDashboardFrame;
import com.uit.librarymanagementapplication.view.admin.lib.CommonUI;
import javax.swing.JFrame;

public class UserController {

  IUserService userService = UserService.getInstance();

    public UserController() {

    }

    public void Login(JFrame currentFrame, String userName, String password, boolean isAdmin) {
        try {
            UserDTO user = userService.login(userName, password, isAdmin);
            currentFrame.dispose();
            if (isAdmin) {
                new AdminDashboardFrame(user);
            } else {
                
            }
        } catch (ApiException e) {
            CommonUI.showError(currentFrame, e);
        }
    }
}
