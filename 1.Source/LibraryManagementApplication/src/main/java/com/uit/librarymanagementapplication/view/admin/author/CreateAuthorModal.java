package com.uit.librarymanagementapplication.view.admin.author;

import com.uit.librarymanagementapplication.controller.AuthorController;
import com.uit.librarymanagementapplication.lib.Constants.ConfirmConsts;
import com.uit.librarymanagementapplication.service.AuthorServices.AuthorService;
import com.uit.librarymanagementapplication.service.AuthorServices.IAuthorService;
import com.uit.librarymanagementapplication.view.lib.CommonUI;
import javax.swing.*;
import java.awt.*;

public class CreateAuthorModal extends JDialog {

    IAuthorService authorService = AuthorService.getInstance();
    AuthorController authorController = new AuthorController();
    private JPanel contentPanel;

    private JTextField txtAuthorName;
    private JButton btnSave, btnCancel;

    public CreateAuthorModal(JFrame parent, JPanel contentPanel) {
        super(parent, "Create new Author", true);
        initUI();
        setLocationRelativeTo(parent);
        this.contentPanel = contentPanel;
    }

    private void initUI() {
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblAuthorName = new JLabel("Author Name:");
        lblAuthorName.setPreferredSize(new Dimension(100, 25));

        txtAuthorName = new JTextField();
        txtAuthorName.setPreferredSize(new Dimension(220, 25));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(lblAuthorName, gbc);

        gbc.gridx = 1;
        formPanel.add(txtAuthorName, gbc);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        btnCancel.setForeground(Color.RED);
        btnCancel.setFocusPainted(false);
        btnCancel.setOpaque(true);
        btnCancel.setContentAreaFilled(true);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        footerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            String name = txtAuthorName.getText().trim();
            if (!name.isEmpty()) {
                int response = CommonUI.showConfirmDialog(this, ConfirmConsts.CONFIRM_CONTENT, ConfirmConsts.CONFIRM_TITLE);
                if (response == JOptionPane.YES_OPTION) {
                    boolean isSuccess = authorService.createAuthors(name);
                    if (isSuccess) {
                        CommonUI.showSuccess(this, "Create author successful");
                        dispose();
                        authorController.Init(contentPanel, true);
                    } else {
                        CommonUI.showError(this, "Create author failed");
                    }
                }
            } else {
                CommonUI.showAlerValidate(this, "Name can not empty");
            }
        });

        btnCancel.addActionListener(e -> dispose());
    }
}
