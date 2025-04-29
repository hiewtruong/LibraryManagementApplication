/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.controller;

import com.uit.librarymanagementapplication.service.AuthorServices.AuthorService;
import com.uit.librarymanagementapplication.service.AuthorServices.IAuthorService;
import com.uit.librarymanagementapplication.service.UserServices.UserService;
import com.uit.librarymanagementapplication.view.admin.author.AuthorPanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author hieutruong
 */
public class AuthorController {

    IAuthorService authorService = AuthorService.getInstance();

    public AuthorController() {

    }

    public void Init(JPanel contentPanel) {
        AuthorPanel authorPanel = new AuthorPanel();
        contentPanel.removeAll();
        contentPanel.add(authorPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
