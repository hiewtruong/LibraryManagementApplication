package com.uit.librarymanagementapplication.view.admin.category;

import com.uit.librarymanagementapplication.domain.DAO.GenreCategoryDAO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.entity.GenreCategory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CategoryPanel extends JPanel {
    private List<GenreCategory> allGenreCategories;
    private UserDTO user;
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private Timer searchTimer;
    private JButton deleteButton;

    private GenreCategoryDAO genreCategoryDAO = new GenreCategoryDAO();

    public CategoryPanel(UserDTO user) {
        this.allGenreCategories = genreCategoryDAO.selectAll();
        this.user = user;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Management of Genre Categories"),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)
        ));
        initUI(this.allGenreCategories);
    }

    private void initUI(List<GenreCategory> genreCategories) {
        // North panel with gradient background
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        northPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Styled search field
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 20));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        northPanel.add(new JLabel("Search: ") {{
            setFont(new Font("Segoe UI", Font.BOLD, 14));
        }});
        northPanel.add(searchField);

        // Styled new button
        JButton newButton = createStyledButton("New");
        newButton.addActionListener(e -> showCategoryDialog(null));
        northPanel.add(newButton);

        // Styled delete button
        deleteButton = createStyledButton("Delete");
        deleteButton.setEnabled(false); // Disabled by default
        deleteButton.addActionListener(e -> handleDelete());
        northPanel.add(deleteButton);

        add(northPanel, BorderLayout.NORTH);

        // Table setup with custom styling
        String[] columnNames = {"ID", "Name", "Genre", "Created Date", "Created By", "Updated Date", "Updated By"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        categoryTable = new JTable(tableModel);
        categoryTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        categoryTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        categoryTable.setRowHeight(30);
        categoryTable.setAutoCreateRowSorter(true);

        // Enable/disable delete button based on selection
        categoryTable.getSelectionModel().addListSelectionListener(e -> {
            deleteButton.setEnabled(categoryTable.getSelectedRowCount() > 0);
        });

        // Custom cell renderer for alternating row colors
        categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                } else {
                    c.setBackground(new Color(180, 210, 255));
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });

        // Double-click handler
        categoryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && categoryTable.getSelectedRow() >= 0) {
                    int selectedRow = categoryTable.getSelectedRow();
                    int modelRow = categoryTable.convertRowIndexToModel(selectedRow);
                    int id = (Integer) tableModel.getValueAt(modelRow, 0);
                    GenreCategory category = allGenreCategories.stream()
                            .filter(c -> c.getGenreCategoryID() == id)
                            .findFirst()
                            .orElse(null);
                    if (category != null) {
                        showCategoryDialog(category);
                    }
                }
            }
        });

        populateTable(genreCategories);
        JScrollPane scrollPane = new JScrollPane(categoryTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Search functionality with 300ms debounce
        searchTimer = new Timer();
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                scheduleSearch();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                scheduleSearch();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                scheduleSearch();
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(80, 27));
        return button;
    }

    private void handleDelete() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        int modelRow = categoryTable.convertRowIndexToModel(selectedRow);
        int id = (Integer) tableModel.getValueAt(modelRow, 0);
        GenreCategory category = allGenreCategories.stream()
                .filter(c -> c.getGenreCategoryID() == id)
                .findFirst()
                .orElse(null);

        if (category == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to mark '" + category.getNameCategory() + "' as deleted?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            category.setIsDelete(1);
            category.setUpdateBy(user.getEmail());
            category.setUpdateDt(new Date());
            genreCategoryDAO.update(category);

            // Refresh table
            allGenreCategories = genreCategoryDAO.selectAll();
            populateTable(allGenreCategories);
            deleteButton.setEnabled(false);
        }
    }

    private void scheduleSearch() {
        searchTimer.cancel();
        searchTimer = new Timer();
        searchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    String searchText = searchField.getText().toLowerCase().trim();
                    filterTable(searchText);
                });
            }
        }, 300);
    }

    private void filterTable(String searchText) {
        tableModel.setRowCount(0);
        List<GenreCategory> filteredCategories = allGenreCategories.stream()
                .filter(category -> searchText.isEmpty() ||
                        category.getNameCategory().toLowerCase().contains(searchText) ||
                        category.getGenreCategory().toLowerCase().contains(searchText))
                .toList();
        populateTable(filteredCategories);
    }

    private void populateTable(List<GenreCategory> categories) {
        tableModel.setRowCount(0);
        for (GenreCategory category : categories) {
            tableModel.addRow(new Object[]{
                    category.getGenreCategoryID(),
                    category.getNameCategory(),
                    category.getGenreCategory(),
                    category.getCreatedDt(),
                    category.getCreatedBy(),
                    category.getUpdateDt(),
                    category.getUpdateBy()
            });
        }
    }

    private void showCategoryDialog(GenreCategory category) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this),
                category == null ? "Add Category" : "Edit Category",
                Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        dialog.setBackground(Color.WHITE);

        // Card-like panel for form
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form fields
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField genreField = new JTextField(20);
        genreField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        if (category != null) {
            nameField.setText(category.getNameCategory());
            genreField.setText(category.getGenreCategory());
        }

        // Layout form
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:") {{
            setFont(new Font("Segoe UI", Font.BOLD, 14));
        }}, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Genre:") {{
            setFont(new Font("Segoe UI", Font.BOLD, 14));
        }}, gbc);
        gbc.gridx = 1;
        formPanel.add(genreField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        JButton saveButton = createStyledButton("Save");
        JButton cancelButton = createStyledButton("Cancel");

        cancelButton.addActionListener(e -> dialog.dispose());
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String genre = genreField.getText().trim();
            if (name.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Name and Genre are required.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            GenreCategory newCategory = new GenreCategory();
            newCategory.setNameCategory(name);
            newCategory.setGenreCategory(genre);
            newCategory.setCreatedBy(user.getEmail());
            newCategory.setUpdateBy(user.getEmail());
            Date now = new Date();
            newCategory.setCreatedDt(now);
            newCategory.setUpdateDt(now);

            if (category != null) {
                newCategory.setGenreCategoryID(category.getGenreCategoryID());
                genreCategoryDAO.update(newCategory);
            } else {
                genreCategoryDAO.insert(newCategory);
                newCategory.setGenreCategoryID(genreCategoryDAO.selectAll()
                        .stream()
                        .mapToInt(GenreCategory::getGenreCategoryID)
                        .max()
                        .orElse(0));
            }

            // Refresh table
            populateTable(genreCategoryDAO.selectAll());
            dialog.dispose();
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        dialog.getContentPane().add(formPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}