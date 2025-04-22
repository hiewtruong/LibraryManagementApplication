package com.uit.librarymanagementapplication.view.admin.category;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CategoryPanel extends JPanel {
    public CategoryPanel() {
        // Thiết lập layout và border
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Quản lý Thể loại"));

        JLabel label = new JLabel("Nội dung quản lý thể loại", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
