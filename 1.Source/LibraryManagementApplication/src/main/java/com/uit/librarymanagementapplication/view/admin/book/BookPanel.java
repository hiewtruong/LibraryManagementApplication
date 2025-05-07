package com.uit.librarymanagementapplication.view.admin.book;

import com.uit.librarymanagementapplication.domain.DAO.BookDAO;
import com.uit.librarymanagementapplication.domain.DAO.GenreCategoryDAO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.entity.Book;
import com.uit.librarymanagementapplication.domain.entity.GenreCategory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class BookPanel extends JPanel {
    private UserDTO user;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private List<Book> allBooks;
    private Timer searchTimer;
    private static final String COVERS_DIR = "book_covers";
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Color BG_COLOR = new Color(240, 240, 240);

    private BookDAO bookDAO = new BookDAO();
    private GenreCategoryDAO genreCategoryDAO = new GenreCategoryDAO();

    public BookPanel(UserDTO user) {
        this.allBooks = bookDAO.selectAll();
        this.user = user;
        initUI(this.allBooks);
    }

    private void initUI(List<Book> books) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Management of Book"),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)
        ));
        // Initialize timer for search delay
        searchTimer = new Timer(true);

        // Top panel for search and add button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Search field with delayed search
        searchField = new JTextField();
        searchField.setFont(FIELD_FONT);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Cancel any existing timer
                searchTimer.cancel();
                searchTimer = new Timer(true);
                
                // Schedule new search after 300ms
                searchTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> filterBooks());
                    }
                }, 300);
            }
        });
        
        // Add button
        JButton addButton = new JButton("Add Book");
        addButton.setFont(FIELD_FONT);
        addButton.setPreferredSize(new Dimension(120, 30));
        addButton.addActionListener(e -> showEditPanel(null));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setFont(LABEL_FONT);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(addButton, BorderLayout.EAST);
        
        // Table setup
        String[] columnNames = {"ID", "Cover", "Title", "Author", "Genre", "Publisher", "Publish Year", "Location", "Display", "Qty OH", "Qty Allocated"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? ImageIcon.class : super.getColumnClass(columnIndex);
            }
        };
        
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(120); // Increased row height for images
        bookTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        bookTable.setAutoCreateRowSorter(true);
        
        // Custom renderer for cover image
        bookTable.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
        
        // Set column widths
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Cover
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Title
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Author
        
        // Double-click handler
        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = bookTable.getSelectedRow();
                    if (row >= 0) {
                        int bookId = (Integer) tableModel.getValueAt(row, 0);
                        Book selectedBook = bookDAO.selectById(bookId);
                        showEditPanel(selectedBook);
                    }
                }
            }
        });
        
        populateTable(books);
        
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void populateTable(List<Book> books) {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        
        for (Book book : books) {
            if (book.getIsDelete() == 1) continue; // Skip deleted books
            ImageIcon coverIcon = null;
            try {
                if (book.getCover() != null && !book.getCover().isEmpty()) {
                    // Load image from resources
                    String resourcePath = "/" + COVERS_DIR + "/" + book.getCover();
                    java.net.URL imageURL = getClass().getResource(resourcePath);
                    if (imageURL != null) {
                        Image image = new ImageIcon(imageURL).getImage();
                        Image scaledImage = image.getScaledInstance(80, 105, Image.SCALE_SMOOTH);
                        coverIcon = new ImageIcon(scaledImage);
                    }
                }
            } catch (Exception e) {
                // If image loading fails, use null (no image)
                System.err.println("Failed to load image for book " + book.getTitle() + ": " + e.getMessage());
            }
            
            Object[] rowData = {
                book.getBookID(),
                coverIcon,
                book.getTitle(),
                book.getAuthor(),
                book.getGenreCategory(),
                book.getPublisher(),
                book.getPublishYear() != null ? dateFormat.format(book.getPublishYear()) : "",
                book.getLocation(),
                book.getIsDisplay() == 1 ? "Yes" : "No",
                book.getQtyOH(),
                book.getQtyAllocated()
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void filterBooks() {
        String searchText = searchField.getText().toLowerCase();
        List<Book> filteredBooks = allBooks.stream()
            .filter(b -> b.getIsDelete() == 0 && (
                b.getTitle().toLowerCase().contains(searchText) ||
                b.getAuthor().toLowerCase().contains(searchText) ||
                b.getGenreCategory().toLowerCase().contains(searchText)))
            .collect(Collectors.toList());
        populateTable(filteredBooks);
    }
    
    private String coverFieldStr = "";
    
    private void showEditPanel(Book book) {
        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), 
            book == null ? "Add Book" : "Edit Book: " + book.getTitle(), 
            Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(850, 800);
        editDialog.getContentPane().setBackground(BG_COLOR);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(BG_COLOR);
        
        // Cover preview panel
        JPanel previewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        previewPanel.setBackground(BG_COLOR);
        JLabel previewLabel = new JLabel();
        previewLabel.setPreferredSize(new Dimension(180, 240));
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        previewPanel.add(previewLabel);
        previewLabel.setText(book == null ? "Choose Cover" : null);
        previewLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_COLOR);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), book == null ? "Book Information" : book.getTitle(), 
            TitledBorder.LEFT, TitledBorder.TOP, LABEL_FONT));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Form fields
        JTextField titleField = new JTextField(book != null ? book.getTitle() : "", 20);
        titleField.setFont(FIELD_FONT);
        JTextField authorField = new JTextField(book != null ? book.getAuthor() : "", 20);
        authorField.setFont(FIELD_FONT);
        JButton chooseCoverButton = new JButton("Choose Cover");
        chooseCoverButton.setFont(FIELD_FONT);
        
        // Initialize JList for multiple category selection
        List<GenreCategory> allCategories = genreCategoryDAO.selectAll();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (GenreCategory category : allCategories) {
            listModel.addElement(category.getNameCategory());
        }
        JList<String> genreList = new JList<>(listModel);
        genreList.setFont(FIELD_FONT);
        genreList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        genreList.setVisibleRowCount(4);
        
        // Pre-select categories for existing book
        if (book != null && book.getGenreCategory() != null && !book.getGenreCategory().isEmpty()) {
            String[] selectedCategories = book.getGenreCategory().split(",");
            int[] selectedIndices = new int[selectedCategories.length];
            for (int i = 0; i < selectedCategories.length; i++) {
                String category = selectedCategories[i].trim();
                int index = allCategories.stream()
                    .map(GenreCategory::getGenreCategoryID)
                    .collect(Collectors.toList())
                    .indexOf(Integer.parseInt(category));
                if (index != -1) {
                    selectedIndices[i] = index;
                }
            }
            genreList.setSelectedIndices(selectedIndices);
        }
        
        JScrollPane genreScrollPane = new JScrollPane(genreList);
        genreScrollPane.setPreferredSize(new Dimension(200, 100));
        
        JTextField publisherField = new JTextField(book != null ? book.getPublisher() : "", 20);
        publisherField.setFont(FIELD_FONT);
        JTextField yearField = new JTextField(book != null && book.getPublishYear() != null ? 
            new SimpleDateFormat("yyyy").format(book.getPublishYear()) : "", 20);
        yearField.setFont(FIELD_FONT);
        JTextField locationField = new JTextField(book != null ? book.getLocation() : "", 20);
        locationField.setFont(FIELD_FONT);
        JCheckBox displayCheck = new JCheckBox("Display", book != null && book.getIsDisplay() == 1);
        displayCheck.setFont(FIELD_FONT);
        displayCheck.setBackground(BG_COLOR);
        JTextField qtyOHField = new JTextField(book != null ? String.valueOf(book.getQtyOH()) : "0", 20);
        qtyOHField.setFont(FIELD_FONT);
        JTextField qtyAllocatedField = new JTextField(book != null ? String.valueOf(book.getQtyAllocated()) : "0", 20);
        qtyAllocatedField.setFont(FIELD_FONT);
        JTextArea landingPageTextArea = new JTextArea(book != null ? book.getLandingPage() : "", 5, 20);
        landingPageTextArea.setFont(FIELD_FONT);
        landingPageTextArea.setLineWrap(true);
        landingPageTextArea.setWrapStyleWord(true);
        landingPageTextArea.setRows(3);
        JTextField hashtagField = new JTextField(book != null ? book.getHashtag() : "", 20);
        hashtagField.setFont(FIELD_FONT);

        // Load initial cover preview
        if (book != null && book.getCover() != null && !book.getCover().isEmpty()) {
            try {
                String resourcePath = "/" + COVERS_DIR + "/" + book.getCover();
                java.net.URL imageURL = getClass().getResource(resourcePath);
                if (imageURL != null) {
                    Image image = new ImageIcon(imageURL).getImage();
                    Image scaledImage = image.getScaledInstance(180, 240, Image.SCALE_SMOOTH);
                    previewLabel.setIcon(new ImageIcon(scaledImage));
                }
            } catch (Exception e) {
                previewLabel.setIcon(null);
            }
        }
        
        // File chooser for cover image
        previewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Image files", "jpg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(editDialog);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // coverField.setText(selectedFile.getAbsolutePath());
                    coverFieldStr = selectedFile.getAbsolutePath();
                    try {
                        Image image = new ImageIcon(selectedFile.getAbsolutePath()).getImage();
                        Image scaledImage = image.getScaledInstance(180, 240, Image.SCALE_SMOOTH);
                        previewLabel.setIcon(new ImageIcon(scaledImage));
                        previewLabel.setText(null);
                    } catch (Exception ex) {
                        previewLabel.setIcon(null);
                        JOptionPane.showMessageDialog(editDialog, 
                            "Error loading preview: " + ex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // Add form components
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(LABEL_FONT);
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(titleField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(LABEL_FONT);
        formPanel.add(authorLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(authorField, gbc);
        
        // gbc.gridx = 0;
        // gbc.gridy = 2;
        // gbc.gridwidth = 1;
        // JLabel coverLabel = new JLabel("Cover:");
        // coverLabel.setFont(LABEL_FONT);
        // formPanel.add(coverLabel, gbc);
        // gbc.gridx = 1;
        // formPanel.add(coverField, gbc);
        // gbc.gridx = 2;
        // formPanel.add(chooseCoverButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel hashTagJLabel = new JLabel("Hash Tag:");
        hashTagJLabel.setFont(LABEL_FONT);
        formPanel.add(hashTagJLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(hashtagField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JLabel genreJLabel = new JLabel("Category:");
        genreJLabel.setFont(LABEL_FONT);
        formPanel.add(genreJLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(genreScrollPane, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JLabel publisherLabel = new JLabel("Publisher:");
        publisherLabel.setFont(LABEL_FONT);
        formPanel.add(publisherLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(publisherField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JLabel yearLabel = new JLabel("Publish Year:");
        yearLabel.setFont(LABEL_FONT);
        formPanel.add(yearLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(yearField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(LABEL_FONT);
        formPanel.add(locationLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(locationField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        JLabel displayLabel = new JLabel("Display:");
        displayLabel.setFont(LABEL_FONT);
        formPanel.add(displayLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(displayCheck, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        JLabel qtyOHLabel = new JLabel("Quantity On Hand:");
        qtyOHLabel.setFont(LABEL_FONT);
        formPanel.add(qtyOHLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(qtyOHField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        JLabel qtyAllocatedLabel = new JLabel("Quantity Allocated:");
        qtyAllocatedLabel.setFont(LABEL_FONT);
        formPanel.add(qtyAllocatedLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(qtyAllocatedField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        JLabel landingPageLabel = new JLabel("Landing page:");
        landingPageLabel.setFont(LABEL_FONT);
        formPanel.add(landingPageLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(landingPageTextArea, gbc);
        
        // Buttons
        JButton saveButton = new JButton("Save");
        saveButton.setFont(FIELD_FONT);
        saveButton.setPreferredSize(new Dimension(100, 30));
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(FIELD_FONT);
        deleteButton.setPreferredSize(new Dimension(100, 30));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(FIELD_FONT);
        cancelButton.setPreferredSize(new Dimension(100, 30));
        
        saveButton.addActionListener(e -> {
            // Validate inputs
            if (titleField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(editDialog, 
                    "Title is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int qtyOH, qtyAllocated;
            try {
                qtyOH = Integer.parseInt(qtyOHField.getText().trim());
                qtyAllocated = Integer.parseInt(qtyAllocatedField.getText().trim());
                if (qtyOH < 0 || qtyAllocated < 0) {
                    throw new NumberFormatException("Quantities must be non-negative");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, 
                    "Invalid quantity format: " + ex.getMessage(), 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                // Handle cover image
                String storedCoverPath = book != null ? book.getCover() : "";
                if (!coverFieldStr.isEmpty()) {
                    // Create book_covers directory in resources if it doesn't exist
                    String resourceDir = "src/main/resources/" + COVERS_DIR;
                    Path coversDir = Paths.get(resourceDir);
                    if (!Files.exists(coversDir)) {
                        Files.createDirectories(coversDir);
                    }
                    
                    File sourceFile = new File(coverFieldStr);
                    if (sourceFile.exists()) {
                        String fileName = sourceFile.getName();
                        Path targetPath = Paths.get(resourceDir, fileName);
                        Files.copy(sourceFile.toPath(), targetPath.toAbsolutePath(), 
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        storedCoverPath = fileName;
                        
                        // Verify the file exists
                        if (!Files.exists(targetPath)) {
                            throw new IOException("Failed to copy image to " + targetPath);
                        }
                    }
                }
                
                // Create or update Book
                Book newBook = book != null ? book : new Book();
                newBook.setTitle(titleField.getText().trim());
                newBook.setAuthor(authorField.getText().trim());
                newBook.setCover(storedCoverPath);
                
                // Handle multiple categories
                String genreCategory = genreList.getSelectedValuesList().stream()
                    .map(category -> allCategories.stream()
                        .filter(c -> c.getNameCategory().equals(category))
                        .findFirst()
                        .map(GenreCategory::getGenreCategoryID)
                        .orElse(0))
                    .filter(id -> id != 0)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
                newBook.setGenreCategory(genreCategory);
                
                newBook.setPublisher(publisherField.getText().trim());
                newBook.setLocation(locationField.getText().trim());
                newBook.setIsDisplay(displayCheck.isSelected() ? 1 : 0);
                newBook.setQtyOH(qtyOH);
                newBook.setQtyAllocated(qtyAllocated);
                newBook.setIsDelete(0); // Ensure not deleted
                newBook.setLandingPage(landingPageTextArea.getText().trim());
                newBook.setHashtag(hashtagField.getText().trim());
                
                // Parse publish year
                try {
                    String yearText = yearField.getText().trim();
                    if (!yearText.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                        newBook.setPublishYear(sdf.parse(yearText));
                    } else {
                        newBook.setPublishYear(null);
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(editDialog, 
                        "Invalid year format. Use YYYY (e.g., 2023)", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Set audit fields
                if (book == null) {
                    newBook.setCreatedDt(new java.util.Date());
                    newBook.setCreatedBy(this.user.getUserName());
                }
                newBook.setUpdateDt(new java.util.Date());
                newBook.setUpdateBy(this.user.getUserName());
                
                // Save book
                if (book == null) {
                    bookDAO.insert(newBook);
                } else {
                    bookDAO.update(newBook);
                }
                
                editDialog.dispose();
                // Refresh table
                Timer refreshTimer = new Timer();
                refreshTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> populateTable(bookDAO.selectAll()));
                    }
                }, 300);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(editDialog, 
                    "Error saving cover image: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, 
                    "Error saving book: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        deleteButton.addActionListener(e -> {
            if (book == null) {
                JOptionPane.showMessageDialog(editDialog, 
                    "Cannot delete a new book", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(editDialog, 
                "Are you sure you want to delete this book?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    book.setIsDelete(1);
                    book.setUpdateDt(new java.util.Date());
                    book.setUpdateBy(this.user.getUserName());
                    bookDAO.update(book);
                    
                    editDialog.dispose();
                    // Refresh table
                    populateTable(bookDAO.selectAll());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(editDialog, 
                        "Error deleting book: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cancelButton.addActionListener(e -> editDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.add(saveButton);
        if (book != null) {
            buttonPanel.add(deleteButton); // Only show delete for existing books
        }
        buttonPanel.add(cancelButton);
        
        // Assemble dialog
        mainPanel.add(previewPanel, BorderLayout.WEST);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.add(mainPanel);
        
        editDialog.setLocationRelativeTo(null);
        editDialog.setVisible(true);
    }
    
    // Custom renderer for image column
    private class ImageRenderer extends JLabel implements TableCellRenderer {
        public ImageRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
            } else {
                setIcon(null);
            }
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            
            return this;
        }
    }
}