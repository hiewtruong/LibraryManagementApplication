package com.uit.librarymanagementapplication.controller.admin;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.view.admin.book.BookPanel;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author phuvn
 */
public class BookController {

    public BookController() {}

    public void Init(JPanel contentPanel, UserDTO user, boolean forceReload) {
        if (forceReload || !(contentPanel.getComponentCount() > 0 && contentPanel.getComponent(0) instanceof BookPanel)) {
            contentPanel.removeAll();
            BookPanel panel = new BookPanel(user);
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(panel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }
}
