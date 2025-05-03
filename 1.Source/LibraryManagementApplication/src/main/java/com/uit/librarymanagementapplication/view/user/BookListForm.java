package com.uit.librarymanagementapplication.view.user;

import com.uit.librarymanagementapplication.controller.BookController;
import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookListForm extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JPanel bookGridPanel;
    private BookController bookController;
    private ExecutorService imageLoader;
    private UserDTO loggedInUser;

    public BookListForm() {
        bookController = new BookController();
        imageLoader = Executors.newFixedThreadPool(5);

        setTitle("Danh sách sách");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchField = new JTextField(30);
        searchButton = new JButton("Tìm kiếm");
        topPanel.add(new JLabel("Tìm sách:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        bookGridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        JScrollPane scrollPane = new JScrollPane(bookGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            List<BookDTO> filteredBooks = bookController.findBooksByName(searchText);
            loadBookData(filteredBooks);
        });

        // Đóng thread pool khi thoát
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                imageLoader.shutdown();
            }
        });

        loadBookData(bookController.getAllBooks());
        this.setVisible(true);

    }

    private void loadBookData(List<BookDTO> books) {
        bookGridPanel.removeAll();

        if (books.isEmpty()) {
            JLabel noResults = new JLabel("Không tìm thấy sách nào", SwingConstants.CENTER);
            noResults.setFont(new Font("Arial", Font.BOLD, 18));
            bookGridPanel.setLayout(new BorderLayout());
            bookGridPanel.add(noResults, BorderLayout.CENTER);
        } else {
            bookGridPanel.setLayout(new GridLayout(0, 3, 15, 15));
            for (BookDTO book : books) {
                JPanel bookPanel = createBookPanel(book);
                bookGridPanel.add(bookPanel);
            }
        }

        bookGridPanel.revalidate();
        bookGridPanel.repaint();
    }

    private JPanel createBookPanel(BookDTO book) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);

        JLabel coverLabel = new JLabel("Đang tải...", SwingConstants.CENTER);
        coverLabel.setPreferredSize(new Dimension(150, 200));
        coverLabel.setOpaque(true);
        coverLabel.setBackground(new Color(230, 230, 230));
        panel.add(coverLabel, BorderLayout.CENTER);

        if (book.getCover() != null && !book.getCover().trim().isEmpty()) {
            loadImageAsync(book.getCover(), coverLabel);
        } else {
            coverLabel.setText("Không có hình");
        }

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(new JLabel("Tên: " + book.getTitle()));
        infoPanel.add(new JLabel("Tác giả: " + book.getAuthor()));
        infoPanel.add(new JLabel("Thể loại: " + book.getGenreCategory()));

        JButton detailButton = new JButton("Xem chi tiết");
        detailButton.setBackground(new Color(0, 120, 215));
        detailButton.setForeground(Color.WHITE);
        detailButton.setFocusPainted(false);
        detailButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        detailButton.addActionListener(e -> new BookDetailsForm(book, loggedInUser).setVisible(true));

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setOpaque(false);
        bottomPanel.add(infoPanel, BorderLayout.CENTER);
        bottomPanel.add(detailButton, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void loadImageAsync(String path, JLabel label) {
        imageLoader.execute(() -> {
            try {
                java.net.URL url = new java.net.URL(path);
                ImageIcon icon = new ImageIcon(url);
                if (icon.getIconWidth() <= 0) {
                    throw new Exception("Không hợp lệ");
                }
                Image scaled = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                SwingUtilities.invokeLater(() -> {
                    label.setIcon(new ImageIcon(scaled));
                    label.setText(null);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    label.setText("Lỗi tải hình");
                });
            }
        });
    }

}
