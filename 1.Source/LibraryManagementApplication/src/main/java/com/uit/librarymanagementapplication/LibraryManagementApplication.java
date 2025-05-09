package com.uit.librarymanagementapplication;

import com.uit.librarymanagementapplication.controller.UserController;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.repository.UserRepositories.UserRepository;
import com.uit.librarymanagementapplication.service.UserServices.UserService;
import com.uit.librarymanagementapplication.view.admin.AdminDashboardFrame;
import com.uit.librarymanagementapplication.view.admin.AdminLoginFrame;
import com.uit.librarymanagementapplication.view.user.BookListForm;
import com.uit.librarymanagementapplication.view.user.UserLoginFrame;
import java.sql.Connection;
import java.sql.SQLException;

public class LibraryManagementApplication {

    public static void main(String[] args) {
        int finalMode = 1;
        javax.swing.SwingUtilities.invokeLater(() -> {
            if (finalMode == 1) {
                new AdminLoginFrame();
            } else {
                new BookListForm();
            }
        });
    }
}
