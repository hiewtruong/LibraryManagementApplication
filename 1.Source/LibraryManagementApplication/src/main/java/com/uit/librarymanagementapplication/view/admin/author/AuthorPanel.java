package com.uit.librarymanagementapplication.view.admin.author;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AuthorPanel extends JPanel {
    public AuthorPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Quản lý tác giả"));
        JLabel label = new JLabel("Nội dung quản lý tác giả", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
