package com.uit.librarymanagementapplication.view.admin.transactionLoan;

import com.uit.librarymanagementapplication.controller.TransacitonLoanController;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.lib.Constants.DateFormat;
import com.uit.librarymanagementapplication.lib.FormatHelper;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.ITransactionLoanService;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.TransactionLoanService;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class TransactionLoanPanel extends JPanel {

    ITransactionLoanService transService = TransactionLoanService.getInstance();
    TransacitonLoanController controller = new TransacitonLoanController();
    private JPanel contentPanel;

    private JTextField searchField;
    private JButton searchButton;
    private JButton createButton;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JComboBox<String> searchColumnComboBox;
    private Map<String, String> columnMapping;

    // Định nghĩa hằng số cho trạng thái
    public static class TransLoanStatusConsts {
        public static final int BORROW = 0;
        public static final int PAID = 1;
    }

    public TransactionLoanPanel(List<TransactionLoanHeaderDTO> transHeaders, JPanel contentPanel) {
        this.contentPanel = contentPanel;
        initUI(transHeaders);
    }

    private void initUI(List<TransactionLoanHeaderDTO> transHeaders) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Management Transaction Loan"),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)
        ));

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        columnMapping = new HashMap<>();
        columnMapping.put("LoanTicketNumber", "T.LoanTicketNumber");
        columnMapping.put("UserName", "U.UserName");
        columnMapping.put("Email", "U.Email");
        columnMapping.put("Phone", "U.Phone");

        String[] columnDisplayNames = {"LoanTicketNumber", "UserName", "Email", "Phone"};
        searchColumnComboBox = new JComboBox<>(columnDisplayNames);
        searchColumnComboBox.setSelectedItem("LoanTicketNumber");
        searchColumnComboBox.setPreferredSize(new Dimension(250, searchColumnComboBox.getPreferredSize().height));

        JLabel searchLabel = new JLabel("Find:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchTransactions());

        leftPanel.add(searchLabel);
        leftPanel.add(searchColumnComboBox);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        topPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createButton = new JButton("Create");
        createButton.addActionListener(e -> createNewTransaction());
        rightPanel.add(createButton);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Di chuyển cột "Status" lên sau "Loan Ticket Number"
        String[] columnNames = {
            "View", "Loan Header ID", "Loan Ticket Number", "Status", "User Name", "Email",
            "Phone", "Total Qty", "Loan Date", "Return Date", "Created By",
            "Created Date", "Updated By", "Updated Date"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        transactionTable.getTableHeader().setReorderingAllowed(false);
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionTable.setRowHeight(35);
        transactionTable.setShowGrid(true);
        transactionTable.setGridColor(Color.BLACK);

        transactionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setBorder(null);
        for (int i = 0; i < transactionTable.getColumnCount(); i++) {
            if (i == 0) {
                transactionTable.getColumnModel().getColumn(i).setCellRenderer(new HyperlinkRenderer());
            } else {
                transactionTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        transactionTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = transactionTable.columnAtPoint(e.getPoint());
                int row = transactionTable.rowAtPoint(e.getPoint());
                if (column == 0) {
                    TransactionLoanHeaderDTO trans = transHeaders.get(row);
                    controller.ViewTransactionLoan(parentFrame, contentPanel, trans);
                }
            }
        });

        // Cài đặt kích thước cột
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        transactionTable.getColumnModel().getColumn(0).setMinWidth(50);
        transactionTable.getColumnModel().getColumn(0).setMaxWidth(50);

        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        transactionTable.getColumnModel().getColumn(1).setMinWidth(150);
        transactionTable.getColumnModel().getColumn(1).setMaxWidth(150);

        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        transactionTable.getColumnModel().getColumn(2).setMinWidth(200);
        transactionTable.getColumnModel().getColumn(2).setMaxWidth(200);

        // Kích thước cho cột "Status"
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(3).setMinWidth(100);
        transactionTable.getColumnModel().getColumn(3).setMaxWidth(100);

        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        transactionTable.getColumnModel().getColumn(4).setMinWidth(120);
        transactionTable.getColumnModel().getColumn(4).setMaxWidth(120);

        transactionTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        transactionTable.getColumnModel().getColumn(5).setMinWidth(200);
        transactionTable.getColumnModel().getColumn(5).setMaxWidth(200);

        transactionTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        transactionTable.getColumnModel().getColumn(6).setMinWidth(120);
        transactionTable.getColumnModel().getColumn(6).setMaxWidth(120);

        transactionTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(7).setMinWidth(100);
        transactionTable.getColumnModel().getColumn(7).setMaxWidth(100);

        transactionTable.getColumnModel().getColumn(8).setPreferredWidth(80);
        transactionTable.getColumnModel().getColumn(8).setMinWidth(80);
        transactionTable.getColumnModel().getColumn(8).setMaxWidth(80);

        transactionTable.getColumnModel().getColumn(9).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(9).setMinWidth(100);
        transactionTable.getColumnModel().getColumn(9).setMaxWidth(100);

        transactionTable.getColumnModel().getColumn(10).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(10).setMinWidth(100);
        transactionTable.getColumnModel().getColumn(10).setMaxWidth(100);

        transactionTable.getColumnModel().getColumn(11).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(11).setMinWidth(100);
        transactionTable.getColumnModel().getColumn(11).setMaxWidth(100);

        transactionTable.getColumnModel().getColumn(12).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(12).setMinWidth(100);
        transactionTable.getColumnModel().getColumn(12).setMaxWidth(100);

        transactionTable.getColumnModel().getColumn(13).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(13).setMinWidth(100);
        transactionTable.getColumnModel().getColumn(13).setMaxWidth(100);

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        transactionTable.setPreferredScrollableViewportSize(new Dimension(
                transactionTable.getPreferredScrollableViewportSize().width,
                transactionTable.getRowHeight() * 10
        ));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: 0");
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bottomPanel.add(totalLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        loadTransactionData(transHeaders);
    }

    private void loadTransactionData(List<TransactionLoanHeaderDTO> transHeaders) {
        tableModel.setRowCount(0);

        for (TransactionLoanHeaderDTO trans : transHeaders) {
            Object[] row = {
                "View",
                trans.getLoanHeaderID(),
                trans.getLoanTicketNumber(),
                trans.getStatusName(),
                trans.getUseName(),
                trans.getEmail(),
                trans.getPhone(),
                trans.getTotalQty(),
                FormatHelper.formatDate(trans.getLoanDt(), DateFormat.MMddyyyy),
                FormatHelper.formatDate(trans.getLoanReturnDt(), DateFormat.MMddyyyy),
                trans.getCreatedBy(),
                FormatHelper.formatDate(trans.getCreatedDt(), DateFormat.MMddyyyy),
                trans.getUpdateBy(),
                FormatHelper.formatDate(trans.getUpdateDt(), DateFormat.MMddyyyy)
            };
            tableModel.addRow(row);
        }
        totalLabel.setText("Total: " + transHeaders.size());
    }

    private void searchTransactions() {
        String keyword = searchField.getText().trim();
        String selectedColumnDisplay = (String) searchColumnComboBox.getSelectedItem();
        String selectedColumn = columnMapping.get(selectedColumnDisplay);
        List<TransactionLoanHeaderDTO> transHeaders = transService.getAllTransLoanHeaderByKeyword(keyword, selectedColumn);
        loadTransactionData(transHeaders);
    }

    private void createNewTransaction() {
        controller.CreateTransactionLoan(contentPanel, false);
    }

    private class HyperlinkRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setText("<html><a href='#'>" + value + "</a></html>");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.BLUE);
            return label;
        }
    }
}