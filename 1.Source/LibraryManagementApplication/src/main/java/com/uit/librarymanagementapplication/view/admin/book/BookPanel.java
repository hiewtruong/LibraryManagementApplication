package com.uit.librarymanagementapplication.view.admin.book;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class BookPanel extends JPanel {
    public BookPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Màn hình quản lý Sách", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
