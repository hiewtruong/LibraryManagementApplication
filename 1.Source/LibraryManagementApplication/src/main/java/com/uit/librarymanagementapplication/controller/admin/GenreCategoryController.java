package com.uit.librarymanagementapplication.controller.admin;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.service.GenreCategoryServices.GenreCategoryService;
import com.uit.librarymanagementapplication.service.GenreCategoryServices.IGenreCategoryService;
import com.uit.librarymanagementapplication.view.admin.category.CategoryPanel;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author phuvn
 */
public class GenreCategoryController {

    IGenreCategoryService service = GenreCategoryService.getInstance();

    public GenreCategoryController() {}

    public void Init(JPanel contentPanel, UserDTO user, boolean forceReload) {
        if (forceReload || !(contentPanel.getComponentCount() > 0 && contentPanel.getComponent(0) instanceof CategoryPanel)) {
            contentPanel.removeAll();
            CategoryPanel panel = new CategoryPanel(user);
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(panel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }
}
