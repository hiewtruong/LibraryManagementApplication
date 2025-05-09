package com.uit.librarymanagementapplication.view.admin;

import com.uit.librarymanagementapplication.controller.AuthorController;
import com.uit.librarymanagementapplication.controller.TransacitonLoanController;
import com.uit.librarymanagementapplication.controller.UserController;
import com.uit.librarymanagementapplication.controller.admin.BookController;
import com.uit.librarymanagementapplication.controller.admin.GenreCategoryController;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.lib.Constants;
import com.uit.librarymanagementapplication.view.admin.author.AuthorPanel;
import com.uit.librarymanagementapplication.view.admin.book.BookPanel;
import com.uit.librarymanagementapplication.view.admin.category.CategoryPanel;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.CreateTransactionLoanPanel;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.TransactionLoanPanel;
import com.uit.librarymanagementapplication.view.admin.user.UserPanel;
import com.uit.librarymanagementapplication.view.lib.CommonUI;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    private JPanel contentPanel;
    private AuthorController authorController;
    private TransacitonLoanController transacitonLoanController;
    private UserController userController;
    private BookController bookController;
    private GenreCategoryController genreCategoryController;

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

        JButton bookBtn = new JButton("Management Book");
        JButton authorBtn = new JButton("Management Author");
        JButton genreBtn = new JButton("Management Category");
        JButton userBtnUser = new JButton("Management User");
        JButton transactionLoanBtn = new JButton("Management Trans Loan");
        JButton crtTransactionLoanBtn = new JButton("Create Trans Loan");
        JButton exitBtn = new JButton("EXIT");
        exitBtn.setForeground(Color.RED);
        exitBtn.setFocusPainted(false);
        exitBtn.setOpaque(true);
        exitBtn.setContentAreaFilled(true);

        Dimension btnSize = new Dimension(180, 30);
        Insets btnInsets = new Insets(5, 10, 5, 10);

        for (JButton btn : new JButton[]{bookBtn, authorBtn, genreBtn, userBtnUser, transactionLoanBtn, crtTransactionLoanBtn}) {
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
        topRightPanel.setOpaque(false);
        JLabel userLabel = new JLabel(user.getUserName());
        JLabel userLabel1 = new JLabel("Role: " + user.getRoleName());
        userLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        userLabel.setForeground(Color.GRAY);
        userLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        userLabel1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        topRightPanel.add(userLabel);
        topRightPanel.add(userLabel1);
        contentWrapper.add(topRightPanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        contentWrapper.add(contentPanel, BorderLayout.CENTER);
        add(contentWrapper, BorderLayout.CENTER);

        this.authorController = new AuthorController();
        this.transacitonLoanController = new TransacitonLoanController();
        this.userController = new UserController();
        this.bookController = new BookController();
        this.genreCategoryController = new GenreCategoryController();

        bookBtn.addActionListener(e -> {
            bookController.Init(contentPanel, user, false);
        });
        authorBtn.addActionListener(e -> {
            authorController.Init(contentPanel, false);
        });
        genreBtn.addActionListener(e -> {
            genreCategoryController.Init(contentPanel, user, false);
        });
        transactionLoanBtn.addActionListener(e -> {
            transacitonLoanController.Init(contentPanel, false);
        });
        crtTransactionLoanBtn.addActionListener(e -> {
            transacitonLoanController.CreateTransactionLoan(contentPanel, false);
        });

        userBtnUser.addActionListener(e -> {
            userController.Init(contentPanel, false);
        });
        exitBtn.addActionListener(e -> handleExit());

        setVisible(true);
    }

    private void setContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void handleExit() {
        int response = CommonUI.showConfirmDialog(this, Constants.ConfirmConsts.CONFIRM_EXIT_CONTENT, Constants.ConfirmConsts.CONFIRM_TITLE);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
