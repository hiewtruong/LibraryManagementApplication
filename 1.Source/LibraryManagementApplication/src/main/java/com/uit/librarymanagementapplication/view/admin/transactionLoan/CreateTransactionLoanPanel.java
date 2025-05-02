package com.uit.librarymanagementapplication.view.admin.transactionLoan;

import com.uit.librarymanagementapplication.controller.TransacitonLoanController;
import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderRequestDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailRequestDTO;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.ITransactionLoanService;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.TransactionLoanService;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

public class CreateTransactionLoanPanel extends JPanel {

    private JPanel contentPanel;
    private JLabel lblSelectedUser;
    private JLabel lblUserPrompt;
    private JFormattedTextField loanReturnDtField;
    private DefaultTableModel availableBooksModel;
    private DefaultTableModel selectedBooksModel;
    private List<BookDTO> selectedBooksList;
    private List<BookDTO> allBooks;
    private JTextField searchField;
    private JTextField searchFieldSelected;
    private TransacitonLoanController transController = new TransacitonLoanController();
    private ITransactionLoanService transactionLoanService = TransactionLoanService.getInstance();
    private TransactionLoanHeaderRequestDTO transactionRequestDTO;
    private JLabel lblTotalQuantity;
    private JTable availableBooksTable; // Thêm biến instance để lưu availableBooksTable
    private JTable selectedBooksTable;  // Thêm biến instance để lưu selectedBooksTable

    public CreateTransactionLoanPanel(JPanel contentPane, List<UserRoleDTO> users, List<BookDTO> books) {
        this.contentPanel = contentPane;
        this.selectedBooksList = new ArrayList<>();
        this.allBooks = new ArrayList<>(books);
        this.transactionRequestDTO = new TransactionLoanHeaderRequestDTO();
        initUI(users, books);
    }

    private void initUI(List<UserRoleDTO> users, List<BookDTO> books) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Create Transaction Loan"));
        setBackground(new Color(242, 242, 242));

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(242, 242, 242));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(242, 242, 242));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(242, 242, 242));

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.setBackground(new Color(242, 242, 242));
        JLabel lblUser = new JLabel("User:");
        lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
        userPanel.add(lblUser);

        lblUserPrompt = new JLabel("Please Choose User");
        lblUserPrompt.setFont(new Font("Arial", Font.PLAIN, 14));
        userPanel.add(lblUserPrompt);

        lblSelectedUser = new JLabel("");
        lblSelectedUser.setFont(new Font("Arial", Font.PLAIN, 14));
        userPanel.add(lblSelectedUser);

        JButton btnChooseUser = new JButton("Choose");
        btnChooseUser.setFont(new Font("Arial", Font.PLAIN, 14));
        btnChooseUser.addActionListener(e -> openUserSelectionDialog(users));
        userPanel.add(btnChooseUser);

        leftPanel.add(userPanel);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(new Color(242, 242, 242));
        JLabel lblLoanReturnDt = new JLabel("Loan Return Date:");
        lblLoanReturnDt.setFont(new Font("Arial", Font.PLAIN, 14));
        datePanel.add(lblLoanReturnDt);

        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            loanReturnDtField = new JFormattedTextField(dateMask);
            loanReturnDtField.setFont(new Font("Arial", Font.PLAIN, 14));
            loanReturnDtField.setPreferredSize(new Dimension(150, 30));
            datePanel.add(loanReturnDtField);

            JLabel lblDateFormat = new JLabel("(Format MM/dd/YYYY)");
            lblDateFormat.setFont(new Font("Arial", Font.PLAIN, 14));
            lblDateFormat.setForeground(Color.GRAY);
            datePanel.add(lblDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            loanReturnDtField = new JFormattedTextField();
            loanReturnDtField.setPreferredSize(new Dimension(150, 30));
            datePanel.add(loanReturnDtField);
        }

        leftPanel.add(datePanel);

        JPanel totalQtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalQtyPanel.setBackground(new Color(242, 242, 242));
        JLabel lblTotalQtyLabel = new JLabel("Total Quantity:");
        lblTotalQtyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        totalQtyPanel.add(lblTotalQtyLabel);

        lblTotalQuantity = new JLabel("0");
        lblTotalQuantity.setFont(new Font("Arial", Font.PLAIN, 14));
        totalQtyPanel.add(lblTotalQuantity);

        leftPanel.add(totalQtyPanel);

        topPanel.add(leftPanel, BorderLayout.WEST);

        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        savePanel.setBackground(new Color(242, 242, 242));
        JButton btnSave = new JButton("Save");
        btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSave.setPreferredSize(new Dimension(80, 30));
        btnSave.addActionListener(e -> saveTransaction());
        savePanel.add(btnSave);

        topPanel.add(savePanel, BorderLayout.EAST);

        contentPanel.add(topPanel, BorderLayout.NORTH);

        JPanel tablesPanel = new JPanel(new GridBagLayout());
        tablesPanel.setBackground(new Color(242, 242, 242));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 10);

        JPanel availableBooksInnerPanel = new JPanel(new BorderLayout(5, 5));
        availableBooksInnerPanel.setBackground(new Color(242, 242, 242));
        availableBooksInnerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Available Books"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(new Color(242, 242, 242));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        inputPanel.setBackground(new Color(242, 242, 242));
        JLabel lblSearch = new JLabel("Search by Title:");
        lblSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblSearch);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(150, 30));
        inputPanel.add(searchField);

        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSearch.addActionListener(e -> performSearch());
        inputPanel.add(btnSearch);

        searchPanel.add(inputPanel, BorderLayout.WEST);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRefresh.addActionListener(e -> refreshAvailableBooks());
        searchPanel.add(btnRefresh, BorderLayout.EAST);

        availableBooksInnerPanel.add(searchPanel, BorderLayout.NORTH);

        String[] bookColumns = {"Book ID", "Title", "Author", "Genre", "Publisher", "Publish Year"};
        availableBooksModel = new DefaultTableModel(bookColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        availableBooksTable = new JTable(availableBooksModel); // Lưu vào biến instance
        availableBooksTable.setFont(new Font("Arial", Font.PLAIN, 14));
        availableBooksTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        availableBooksTable.setRowHeight(25);
        availableBooksTable.setShowGrid(true);
        availableBooksTable.setGridColor(Color.LIGHT_GRAY);

        availableBooksTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int bookId = (int) table.getModel().getValueAt(row, 0);
                BookDTO book = allBooks.stream().filter(b -> b.getBookID() == bookId).findFirst().orElse(null);
                if (book != null && book.isIsOutOfStock()) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        TableColumn bookIdColumn = availableBooksTable.getColumnModel().getColumn(0);
        availableBooksTable.getColumnModel().removeColumn(bookIdColumn);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        for (BookDTO book : books) {
            String formattedPublishYear = dateFormat.format(book.getPublishYear());
            Object[] rowData = {
                book.getBookID(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenreCategory(),
                book.getPublisher(),
                formattedPublishYear
            };
            availableBooksModel.addRow(rowData);
        }

        JScrollPane availableBooksScrollPane = new JScrollPane(availableBooksTable);
        availableBooksScrollPane.setColumnHeaderView(availableBooksTable.getTableHeader());
        availableBooksInnerPanel.add(availableBooksScrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        tablesPanel.add(availableBooksInnerPanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(242, 242, 242));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton btnMoveBook = new JButton("Move");
        btnMoveBook.setFont(new Font("Arial", Font.BOLD, 14));
        btnMoveBook.addActionListener(e -> moveBookToSelected());
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(btnMoveBook);
        buttonPanel.add(Box.createVerticalGlue());

        JButton btnRemoveBook = new JButton("Remove");
        btnRemoveBook.setFont(new Font("Arial", Font.BOLD, 14));
        btnRemoveBook.addActionListener(e -> moveBookToAvailable());
        buttonPanel.add(btnRemoveBook);
        buttonPanel.add(Box.createVerticalGlue());

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        tablesPanel.add(buttonPanel, gbc);

        JPanel selectedBooksInnerPanel = new JPanel(new BorderLayout(5, 5));
        selectedBooksInnerPanel.setBackground(new Color(242, 242, 242));
        selectedBooksInnerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Selected Books"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JPanel searchPanelSelected = new JPanel(new BorderLayout(5, 5));
        searchPanelSelected.setBackground(new Color(242, 242, 242));

        JPanel inputPanelSelected = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        inputPanelSelected.setBackground(new Color(242, 242, 242));
        JLabel lblSearchSelected = new JLabel("Search by Title:");
        lblSearchSelected.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanelSelected.add(lblSearchSelected);

        searchFieldSelected = new JTextField();
        searchFieldSelected.setFont(new Font("Arial", Font.PLAIN, 14));
        searchFieldSelected.setPreferredSize(new Dimension(150, 30));
        inputPanelSelected.add(searchFieldSelected);

        JButton btnSearchSelected = new JButton("Search");
        btnSearchSelected.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSearchSelected.addActionListener(e -> performSearchSelected());
        inputPanelSelected.add(btnSearchSelected);

        searchPanelSelected.add(inputPanelSelected, BorderLayout.WEST);

        JButton btnRefreshSelected = new JButton("Refresh");
        btnRefreshSelected.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRefreshSelected.addActionListener(e -> refreshSelectedBooks());
        searchPanelSelected.add(btnRefreshSelected, BorderLayout.EAST);

        selectedBooksInnerPanel.add(searchPanelSelected, BorderLayout.NORTH);

        selectedBooksModel = new DefaultTableModel(bookColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        selectedBooksTable = new JTable(selectedBooksModel); // Lưu vào biến instance
        selectedBooksTable.setFont(new Font("Arial", Font.PLAIN, 14));
        selectedBooksTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        selectedBooksTable.setRowHeight(25);
        selectedBooksTable.setShowGrid(true);
        selectedBooksTable.setGridColor(Color.LIGHT_GRAY);

        TableColumn bookIdColumnSelected = selectedBooksTable.getColumnModel().getColumn(0);
        selectedBooksTable.getColumnModel().removeColumn(bookIdColumnSelected);

        JScrollPane selectedBooksScrollPane = new JScrollPane(selectedBooksTable);
        selectedBooksScrollPane.setColumnHeaderView(selectedBooksTable.getTableHeader());
        selectedBooksInnerPanel.add(selectedBooksScrollPane, BorderLayout.CENTER);

        gbc.gridx = 2;
        gbc.weightx = 1.0;
        tablesPanel.add(selectedBooksInnerPanel, gbc);

        contentPanel.add(tablesPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void updateTotalQuantity() {
        lblTotalQuantity.setText(String.valueOf(selectedBooksList.size()));
        transactionRequestDTO.setTotalQty(selectedBooksList.size());
    }

    private void performSearch() {
        String keyword = searchField.getText().trim().toLowerCase();
        availableBooksModel.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        for (BookDTO book : allBooks) {
            if (selectedBooksList.stream().noneMatch(selectedBook -> selectedBook.getBookID() == book.getBookID())) {
                if (keyword.isEmpty() || book.getTitle().toLowerCase().contains(keyword)) {
                    String formattedPublishYear = dateFormat.format(book.getPublishYear());
                    Object[] rowData = {
                        book.getBookID(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenreCategory(),
                        book.getPublisher(),
                        formattedPublishYear
                    };
                    availableBooksModel.addRow(rowData);
                }
            }
        }
    }

    private void performSearchSelected() {
        String keyword = searchFieldSelected.getText().trim().toLowerCase();
        selectedBooksModel.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        for (BookDTO book : selectedBooksList) {
            if (keyword.isEmpty() || book.getTitle().toLowerCase().contains(keyword)) {
                String formattedPublishYear = dateFormat.format(book.getPublishYear());
                Object[] rowData = {
                    book.getBookID(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenreCategory(),
                    book.getPublisher(),
                    formattedPublishYear
                };
                selectedBooksModel.addRow(rowData);
            }
        }
    }

    private void refreshAvailableBooks() {
        searchField.setText("");
        availableBooksModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        for (BookDTO book : allBooks) {
            if (selectedBooksList.stream().noneMatch(selectedBook -> selectedBook.getBookID() == book.getBookID())) {
                String formattedPublishYear = dateFormat.format(book.getPublishYear());
                Object[] rowData = {
                    book.getBookID(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenreCategory(),
                    book.getPublisher(),
                    formattedPublishYear
                };
                availableBooksModel.addRow(rowData);
            }
        }
    }

    private void refreshSelectedBooks() {
        searchFieldSelected.setText("");
        selectedBooksModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        for (BookDTO book : selectedBooksList) {
            String formattedPublishYear = dateFormat.format(book.getPublishYear());
            Object[] rowData = {
                book.getBookID(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenreCategory(),
                book.getPublisher(),
                formattedPublishYear
            };
            selectedBooksModel.addRow(rowData);
        }
        updateTotalQuantity();
    }

    private void openUserSelectionDialog(List<UserRoleDTO> users) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        TransactionUserChooseModal dialog = new TransactionUserChooseModal(parentFrame, contentPanel, users);
        dialog.setVisible(true);

        UserRoleDTO selectedUser = dialog.getSelectedUser();
        if (selectedUser != null) {
            lblSelectedUser.setText(selectedUser.getUserName());
            lblUserPrompt.setText("");
            transactionRequestDTO.setUserID(selectedUser.getUserID());
        }
    }

    private void saveTransaction() {
        if (lblSelectedUser.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose a user!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Date returnDate = parseLoanReturnDate();
        if (returnDate == null) {
            JOptionPane.showMessageDialog(this, "Please enter a valid loan return date (MM/dd/yyyy)!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedBooksList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one book!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        transactionRequestDTO.setLoanReturnDt(returnDate);

        List<TransactionLoanDetailRequestDTO> loanDetails = new ArrayList<>();
        for (BookDTO book : selectedBooksList) {
            TransactionLoanDetailRequestDTO detail = new TransactionLoanDetailRequestDTO();
            detail.setLoadBookID(book.getBookID());
            loanDetails.add(detail);
        }
        transactionRequestDTO.setLoanDetails(loanDetails);

        try {
            transactionLoanService.createTransactionLoan(transactionRequestDTO);
            JOptionPane.showMessageDialog(this, "Transaction saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            lblSelectedUser.setText("");
            lblUserPrompt.setText("Please Choose User");
            loanReturnDtField.setText("");
            selectedBooksList.clear();
            refreshAvailableBooks();
            refreshSelectedBooks();
            transactionRequestDTO = new TransactionLoanHeaderRequestDTO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save transaction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Date parseLoanReturnDate() {
        String dateStr = loanReturnDtField.getText();
        if (dateStr == null || dateStr.trim().isEmpty() || dateStr.equals("__/__/____")) {
            return null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    private void moveBookToSelected() {
        int selectedRow = availableBooksTable.getSelectedRow();
        if (selectedRow != -1) {
            int bookId = (int) availableBooksModel.getValueAt(selectedRow, 0);
            BookDTO selectedBook = allBooks.stream().filter(b -> b.getBookID() == bookId).findFirst().orElse(null);

            if (selectedBook != null && selectedBook.isIsOutOfStock()) {
                JOptionPane.showMessageDialog(this, "This book is out of stock and cannot be selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Object[] rowData = new Object[availableBooksModel.getColumnCount()];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = availableBooksModel.getValueAt(selectedRow, i);
            }

            selectedBooksModel.addRow(rowData);
            availableBooksModel.removeRow(selectedRow);

            selectedBooksList.add(selectedBook);
            performSearch();
            performSearchSelected();
            updateTotalQuantity();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to add!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void moveBookToAvailable() {
        int selectedRow = selectedBooksTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] rowData = new Object[selectedBooksModel.getColumnCount()];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = selectedBooksModel.getValueAt(selectedRow, i);
            }

            availableBooksModel.addRow(rowData);
            selectedBooksModel.removeRow(selectedRow);

            int bookId = (int) rowData[0];
            selectedBooksList.removeIf(book -> book.getBookID() == bookId);

            performSearch();
            performSearchSelected();
            updateTotalQuantity();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to remove!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Date parseLoanReturnDateFromString(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    public List<BookDTO> getSelectedBooks() {
        return selectedBooksList;
    }

    public String getSelectedUser() {
        return lblSelectedUser.getText();
    }

    public java.util.Date getLoanReturnDt() {
        return parseLoanReturnDate();
    }
}