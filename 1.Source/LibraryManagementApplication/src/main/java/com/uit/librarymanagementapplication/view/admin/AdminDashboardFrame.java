package com.uit.librarymanagementapplication.view.admin;

import com.uit.librarymanagementapplication.domain.DTO.UserDTO;
import com.uit.librarymanagementapplication.view.admin.author.AuthorPanel;
import com.uit.librarymanagementapplication.view.admin.book.BookPanel;
import com.uit.librarymanagementapplication.view.admin.category.CategoryPanel;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    private JPanel contentPanel;

    public AdminDashboardFrame(UserDTO user) {
        setTitle("Admin Dashboard");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menu trái
        JPanel menuWrapper = new JPanel(new BorderLayout());
        menuWrapper.setPreferredSize(new Dimension(240, getHeight()));
        menuWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));

        JButton bookBtn = new JButton("Quản lý Sách");
        JButton authorBtn = new JButton("Quản lý Tác giả");
        JButton genreBtn = new JButton("Quản lý Thể loại");
        JButton exitBtn = new JButton("Thoát");

        Dimension btnSize = new Dimension(180, 30);
        Insets btnInsets = new Insets(5, 10, 5, 10);

        for (JButton btn : new JButton[]{bookBtn, authorBtn, genreBtn}) {
            btn.setMaximumSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMargin(btnInsets);
            menuPanel.add(Box.createVerticalStrut(10));
            menuPanel.add(btn);
        }

        menuPanel.add(Box.createVerticalGlue());
        exitBtn.setMaximumSize(btnSize);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setMargin(btnInsets);
        menuPanel.add(exitBtn);
        menuPanel.add(Box.createVerticalStrut(10));

        menuWrapper.add(menuPanel, BorderLayout.CENTER);
        add(menuWrapper, BorderLayout.WEST);

        // ✅ Nội dung chính có border + padding như menu
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        // Thêm góc phải trên hiển thị tên người dùng
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS));
        topRightPanel.setOpaque(false); // trong suốt
        JLabel userLabel = new JLabel(user.getUserName());
        JLabel userLabel1 = new JLabel("Role: "+user.getRoleName());
        userLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        userLabel.setForeground(Color.GRAY);
        userLabel.setAlignmentX(Component.RIGHT_ALIGNMENT); 
        userLabel1.setAlignmentX(Component.RIGHT_ALIGNMENT); 
        topRightPanel.add(userLabel);
        topRightPanel.add(userLabel1);
        contentWrapper.add(topRightPanel, BorderLayout.NORTH);

        // Nội dung chính
        contentPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Chào mừng Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        contentWrapper.add(contentPanel, BorderLayout.CENTER);
        add(contentWrapper, BorderLayout.CENTER);

        // Sự kiện nút
        bookBtn.addActionListener(e -> setContent(new BookPanel()));
        authorBtn.addActionListener(e -> setContent(new AuthorPanel()));
        genreBtn.addActionListener(e -> setContent(new CategoryPanel()));
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void setContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}