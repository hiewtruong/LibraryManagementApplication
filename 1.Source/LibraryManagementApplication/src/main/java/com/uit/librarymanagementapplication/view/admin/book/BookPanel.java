package com.uit.librarymanagementapplication.view.admin.book;

import com.uit.librarymanagementapplication.domain.DAO.BookDAO;
import com.uit.librarymanagementapplication.domain.DAO.GenreCategoryDAO;
import com.uit.librarymanagementapplication.domain.DTO.Author.AuthorDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.entity.Book;
import com.uit.librarymanagementapplication.domain.entity.GenreCategory;
import com.uit.librarymanagementapplication.service.AuthorServices.AuthorService;
import com.uit.librarymanagementapplication.service.AuthorServices.IAuthorService;
import com.uit.librarymanagementapplication.domain.ImageUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
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
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Color BG_COLOR = new Color(240, 240, 240);

    private BookDAO bookDAO = new BookDAO();
    private GenreCategoryDAO genreCategoryDAO = new GenreCategoryDAO();
    private IAuthorService authorService = AuthorService.getInstance();

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
        searchTimer = new Timer(true);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        searchField = new JTextField();
        searchField.setFont(FIELD_FONT);
        searchField.setPreferredSize(new Dimension(200, 20));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchTimer.cancel();
                searchTimer = new Timer(true);
                searchTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> filterBooks());
                    }
                }, 300);
            }
        });
        
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
        bookTable.setRowHeight(120);
        bookTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        bookTable.setAutoCreateRowSorter(true);
        
        bookTable.getColumnModel().getColumn(1).setCellRenderer(ImageUtils.getImageRenderer());
        
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        
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
            if (book.getIsDelete() == 1) continue;
            ImageIcon coverIcon = null;
            if (book.getCover() != null && !book.getCover().isEmpty()) {
                coverIcon = ImageUtils.loadImage("/book_covers/" + book.getCover(), 80, 105);
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
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(BG_COLOR);
        
        JPanel previewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        previewPanel.setBackground(BG_COLOR);
        JLabel previewLabel = new JLabel();
        previewLabel.setPreferredSize(new Dimension(180, 240));
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        previewPanel.add(previewLabel);
        previewLabel.setText(book == null ? "Choose Cover" : null);
        previewLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_COLOR);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), book == null ? "Book Information" : book.getTitle(), 
            TitledBorder.LEFT, TitledBorder.TOP, LABEL_FONT));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        
        JTextField titleField = new JTextField(book != null ? book.getTitle() : "", 20);
        titleField.setFont(FIELD_FONT);
        
        List<AuthorDTO> authors = authorService.getAuthorByName("");
        DefaultComboBoxModel<String> authorModel = new DefaultComboBoxModel<>();
        for (AuthorDTO author : authors) {
            authorModel.addElement(author.getAuthorName());
        }
        JComboBox<String> authorComboBox = new JComboBox<>(authorModel);
        authorComboBox.setFont(FIELD_FONT);
        
        if (book != null && book.getAuthor() != null && !book.getAuthor().isEmpty()) {
            authorComboBox.setSelectedItem(book.getAuthor());
        }
        
        List<GenreCategory> allCategories = genreCategoryDAO.selectAll();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (GenreCategory category : allCategories) {
            listModel.addElement(category.getNameCategory());
        }
        JList<String> genreList = new JList<>(listModel);
        genreList.setFont(FIELD_FONT);
        genreList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        genreList.setVisibleRowCount(4);
        
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

        if (book != null && book.getCover() != null && !book.getCover().isEmpty()) {
            ImageIcon coverIcon = ImageUtils.loadImage("/book_covers/" + book.getCover(), 180, 240);
            previewLabel.setIcon(coverIcon);
        }
        
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
                    coverFieldStr = selectedFile.getAbsolutePath();
                    ImageIcon previewIcon = ImageUtils.loadImage(coverFieldStr, 180, 240);
                    if (previewIcon != null) {
                        previewLabel.setIcon(previewIcon);
                        previewLabel.setText(null);
                    } else {
                        previewLabel.setIcon(null);
                        JOptionPane.showMessageDialog(editDialog, 
                            "Error loading preview", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
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
        formPanel.add(authorComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel hashTagJLabel = new JLabel("Hash Tag:");
        hashTagJLabel.setFont(LABEL_FONT);
        formPanel.add(hashTagJLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(hashtagField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel genreJLabel = new JLabel("Category:");
        genreJLabel.setFont(LABEL_FONT);
        formPanel.add(genreJLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(genreScrollPane, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JLabel publisherLabel = new JLabel("Publisher:");
        publisherLabel.setFont(LABEL_FONT);
        formPanel.add(publisherLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(publisherField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JLabel yearLabel = new JLabel("Publish Year:");
        yearLabel.setFont(LABEL_FONT);
        formPanel.add(yearLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(yearField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(LABEL_FONT);
        formPanel.add(locationLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(locationField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        JLabel displayLabel = new JLabel("Display:");
        displayLabel.setFont(LABEL_FONT);
        formPanel.add(displayLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(displayCheck, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        JLabel qtyOHLabel = new JLabel("Quantity On Hand:");
        qtyOHLabel.setFont(LABEL_FONT);
        formPanel.add(qtyOHLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(qtyOHField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        JLabel qtyAllocatedLabel = new JLabel("Quantity Allocated:");
        qtyAllocatedLabel.setFont(LABEL_FONT);
        formPanel.add(qtyAllocatedLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(qtyAllocatedField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        JLabel landingPageLabel = new JLabel("Landing page:");
        landingPageLabel.setFont(LABEL_FONT);
        formPanel.add(landingPageLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(landingPageTextArea, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(new JPanel(), gbc);
        
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
                String storedCoverPath = book != null ? book.getCover() : "";
                if (!coverFieldStr.isEmpty()) {
                    storedCoverPath = ImageUtils.saveImage(new File(coverFieldStr));
                }
                
                Book newBook = book != null ? book : new Book();
                newBook.setTitle(titleField.getText().trim());
                String selectedAuthor = (String) authorComboBox.getSelectedItem();
                newBook.setAuthor(selectedAuthor != null ? selectedAuthor : "");
                newBook.setCover(storedCoverPath);
                
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
                newBook.setIsDelete(0);
                newBook.setLandingPage(landingPageTextArea.getText().trim());
                newBook.setHashtag(hashtagField.getText().trim());
                
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
                
                if (book == null) {
                    newBook.setCreatedDt(new java.util.Date());
                    newBook.setCreatedBy(this.user.getUserName());
                }
                newBook.setUpdateDt(new java.util.Date());
                newBook.setUpdateBy(this.user.getUserName());
                
                if (book == null) {
                    bookDAO.insert(newBook);
                } else {
                    bookDAO.update(newBook);
                }
                
                editDialog.dispose();
                // Refresh allBooks and table
                allBooks = bookDAO.selectAll();
                Timer refreshTimer = new Timer();
                refreshTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> {
                            populateTable(allBooks);
                            bookTable.repaint();
                        });
                    }
                }, 500); // Increased delay to ensure file system sync
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
                    allBooks = bookDAO.selectAll();
                    populateTable(allBooks);
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
        buttonPanel.add(cancelButton);
        if (book != null) {
            buttonPanel.add(deleteButton);
        }
        
        mainPanel.add(previewPanel, BorderLayout.WEST);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.add(mainPanel);
        
        editDialog.setLocationRelativeTo(null);
        editDialog.setVisible(true);
    }
}