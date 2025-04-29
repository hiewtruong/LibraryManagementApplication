package com.uit.librarymanagementapplication.view.admin;

import com.uit.librarymanagementapplication.controller.AuthorController;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.view.admin.author.AuthorPanel;
import com.uit.librarymanagementapplication.view.admin.book.BookPanel;
import com.uit.librarymanagementapplication.view.admin.category.CategoryPanel;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.CreateTransactionLoanPanel;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.TransactionLoanPanel;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    private JPanel contentPanel;
    private AuthorController authorController;

    public AdminDashboardFrame(UserDTO user) {
        setTitle("Admin Dashboard");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel menuWrapper = new JPanel(new BorderLayout());
        menuWrapper.setPreferredSize(new Dimension(240, getHeight()));
        menuWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));

        JButton bookBtn = new JButton("Quản lý Sách");
        JButton authorBtn = new JButton("Quản lý Tác giả");
        JButton genreBtn = new JButton("Quản lý Thể loại");
        JButton transactionLoanBtn = new JButton("Quản lý Đơn thuê/mượn sách");
        JButton crtTransactionLoanBtn = new JButton("Tạo đơn thuê/mượn sách");
        JButton exitBtn = new JButton("Thoát");

        Dimension btnSize = new Dimension(180, 30);
        Insets btnInsets = new Insets(5, 10, 5, 10);

        for (JButton btn : new JButton[]{bookBtn, authorBtn, genreBtn,transactionLoanBtn,crtTransactionLoanBtn}) {
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

        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

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

        contentPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Chào mừng Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        contentWrapper.add(contentPanel, BorderLayout.CENTER);
        add(contentWrapper, BorderLayout.CENTER);

        this.authorController = new AuthorController();
        
        bookBtn.addActionListener(e -> setContent(new BookPanel()));
        authorBtn.addActionListener(e -> {
            // Gọi phương thức init từ AuthorController, truyền frame hiện tại
            authorController.Init(contentPanel);
        });
        genreBtn.addActionListener(e -> setContent(new CategoryPanel()));
        transactionLoanBtn.addActionListener(e -> setContent(new TransactionLoanPanel()));
        crtTransactionLoanBtn.addActionListener(e -> setContent(new CreateTransactionLoanPanel()));
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