package com.uit.librarymanagementapplication.view.user;

import com.uit.librarymanagementapplication.controller.BookController;
import com.uit.librarymanagementapplication.controller.UserController;
import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookDetailsForm extends JFrame {

    private final BookDTO book;
    private final ExecutorService imageLoader;
    private JButton rentButton;
    private UserDTO loggedInUser;
    private BookController bookController;
    private UserController controller;

    public BookDetailsForm(BookDTO book, UserDTO loggedInUser) {
        this.book = book;
        this.controller = new UserController(); 
        UserDTO currentUser = controller.getCurrentUser();
        if (currentUser != null) {
            this.loggedInUser = currentUser;
        } else {
            this.loggedInUser = null;
        }
        this.bookController = new BookController();
        this.bookController.setCurrentUser(this.loggedInUser);
        this.imageLoader = Executors.newSingleThreadExecutor();

        setTitle("Chi tiết sách");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createMainPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                imageLoader.shutdown();
            }
        });

        updateRentButtonState();
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel coverLabel = new JLabel("Đang tải...", SwingConstants.CENTER);
        coverLabel.setPreferredSize(new Dimension(300, 400));
        coverLabel.setOpaque(true);
        coverLabel.setBackground(new Color(230, 230, 230));
        if (book.getCover() != null && !book.getCover().trim().isEmpty()) {
            loadImageAsync(book.getCover(), coverLabel);
        } else {
            coverLabel.setText("Không có hình");
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(Color.WHITE);

        infoPanel.add(createLabel(book.getTitle(), 24, true));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createLabel("Tác giả: " + book.getAuthor(), 18, false));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createLabel("Thể loại: " + book.getGenreCategory(), 18, false));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createLabel("Nhà xuất bản: " + book.getPublisher(), 18, false));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createLabel("Năm xuất bản: " + book.getPublishYear(), 18, false));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createLabel("Vị trí: " + book.getLocation(), 18, false));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createLabel("Số lượng còn: " + book.getQtyOH(), 18, false));
        infoPanel.add(Box.createVerticalStrut(10));

        JLabel descLabel = new JLabel("<html><div style='width:300px;'>Mô tả: " + book.getHashtag() + "</div></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoPanel.add(descLabel);

        mainPanel.add(coverLabel, BorderLayout.WEST);
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);

        rentButton = new JButton("Thuê sách");
        styleButton(rentButton, new Color(76, 175, 80));
        rentButton.addActionListener(e -> {
            if (loggedInUser == null) {
                int option = JOptionPane.showConfirmDialog(this,
                        "Bạn cần đăng nhập để thuê sách. Đăng nhập ngay?",
                        "Yêu cầu đăng nhập",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    showLoginForm();
                }
            } else {
                rentBook();
            }
        });

        panel.add(rentButton);
        return panel;
    }

    private void showLoginForm() {
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);

        if (loginDialog.getLoggedInUser() != null) {
            UserDTO user = loginDialog.getLoggedInUser();
            this.loggedInUser = user;
            this.bookController.setCurrentUser(user);

            rentBook();
        }
    }

    private void rentBook() {
        String message = bookController.handleRentBook(loggedInUser.getUserId(), book);

        JOptionPane.showMessageDialog(this, message);

        if (message.equals("Bạn đã thuê sách thành công!")) {
            dispose();
        }

        updateRentButtonState();
    }

    private JLabel createLabel(String text, int fontSize, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, fontSize));
        return label;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void loadImageAsync(String path, JLabel label) {
        imageLoader.execute(() -> {
            try {
                java.net.URL url = new java.net.URL(path);
                ImageIcon icon = new ImageIcon(url);
                if (icon.getIconWidth() <= 0) {
                    throw new Exception("Hình không hợp lệ");
                }
                Image scaled = icon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
                SwingUtilities.invokeLater(() -> {
                    label.setIcon(new ImageIcon(scaled));
                    label.setText(null);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> label.setText("Lỗi tải hình"));
            }
        });
    }

    private void updateRentButtonState() {
        if (book.getQtyOH() <= 0) {
            rentButton.setEnabled(false);
            rentButton.setText("Hết sách");
        } else {
            rentButton.setEnabled(true);
            rentButton.setText("Thuê sách");
        }
    }
}
