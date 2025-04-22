package com.uit.librarymanagementapplication.view.admin.author;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AuthorPanel extends JPanel {
    public AuthorPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Màn hình quản lý Tác giả", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
