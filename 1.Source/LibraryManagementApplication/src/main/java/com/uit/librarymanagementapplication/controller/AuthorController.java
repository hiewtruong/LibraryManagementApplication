package com.uit.librarymanagementapplication.controller;

import com.uit.librarymanagementapplication.domain.DTO.Author.AuthorDTO;
import com.uit.librarymanagementapplication.service.AuthorServices.AuthorService;
import com.uit.librarymanagementapplication.service.AuthorServices.IAuthorService;
import com.uit.librarymanagementapplication.view.admin.author.AuthorPanel;
import com.uit.librarymanagementapplication.view.admin.author.CreateAuthorModal;
import java.awt.BorderLayout;
import java.util.List;
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

    public void Init(JPanel contentPanel, boolean forceReload) {
        if (forceReload || !(contentPanel.getComponentCount() > 0 && contentPanel.getComponent(0) instanceof AuthorPanel)) {
            contentPanel.removeAll();
            List<AuthorDTO> authors = authorService.getAuthorByName("");
            AuthorPanel authorPanel = new AuthorPanel(authors,contentPanel);
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(authorPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public void CreateAuthor(JFrame parentFrame, JPanel contentPanel) {
        CreateAuthorModal dialog = new CreateAuthorModal(parentFrame, contentPanel);
        dialog.setVisible(true);
    }
}
