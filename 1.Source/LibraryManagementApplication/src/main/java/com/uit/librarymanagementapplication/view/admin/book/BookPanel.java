package com.uit.librarymanagementapplication.view.admin.book;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class BookPanel extends JPanel {
    public BookPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Quản lý sách"));
        JLabel label = new JLabel("Nội dung quản lý sách", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
