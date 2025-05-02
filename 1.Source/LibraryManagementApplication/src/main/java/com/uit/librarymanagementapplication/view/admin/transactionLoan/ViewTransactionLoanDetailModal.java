package com.uit.librarymanagementapplication.view.admin.transactionLoan;

import com.uit.librarymanagementapplication.controller.TransacitonLoanController;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.lib.FormatHelper;
import com.uit.librarymanagementapplication.lib.Constants.DateFormat;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.ITransactionLoanService;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.TransactionLoanService;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewTransactionLoanDetailModal extends JDialog {

    ITransactionLoanService transactionLoanService = TransactionLoanService.getInstance();
    TransacitonLoanController transactionLoanController = new TransacitonLoanController();
    private JPanel contentPanel;

    private JButton btnClose;
    private JButton btnRevoke;

    public ViewTransactionLoanDetailModal(JFrame parentFrame, JPanel contentPanel, TransactionLoanHeaderDTO headerDto, List<TransactionLoanDetailDTO> detailsDto) {
        super(parentFrame, "Transaction Loan Details", true);
        this.contentPanel = contentPanel;
        initUI(headerDto, detailsDto);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
    }

    private void initUI(TransactionLoanHeaderDTO headerDto, List<TransactionLoanDetailDTO> detailsDto) {
        setSize(1000, 450);
        setLayout(new BorderLayout(10, 10));

        // Xóa biến backgroundColor và các đoạn setBackground
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Transaction Loan Information-" + headerDto.getLoanTicketNumber()),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                headerPanel.getBorder()
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;

        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font normalFont = new Font("Arial", Font.PLAIN, 14);

        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblLoanTicketNumber = new JLabel("Loan Ticket Number:");
        lblLoanTicketNumber.setFont(boldFont);
        headerPanel.add(lblLoanTicketNumber, gbc);
        gbc.gridx = 1;
        JLabel lblLoanTicketNumberValue = new JLabel(headerDto.getLoanTicketNumber());
        lblLoanTicketNumberValue.setFont(normalFont);
        headerPanel.add(lblLoanTicketNumberValue, gbc);

        gbc.gridx = 2;
        gbc.gridy = row;
        JLabel lblUserName = new JLabel("User Name:");
        lblUserName.setFont(boldFont);
        headerPanel.add(lblUserName, gbc);
        gbc.gridx = 3;
        JLabel lblUserNameValue = new JLabel(headerDto.getUseName());
        lblUserNameValue.setFont(normalFont);
        headerPanel.add(lblUserNameValue, gbc);

        gbc.gridx = 4;
        gbc.gridy = row;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(boldFont);
        headerPanel.add(lblEmail, gbc);
        gbc.gridx = 5;
        JLabel lblEmailValue = new JLabel(headerDto.getEmail());
        lblEmailValue.setFont(normalFont);
        headerPanel.add(lblEmailValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(boldFont);
        headerPanel.add(lblPhone, gbc);
        gbc.gridx = 1;
        JLabel lblPhoneValue = new JLabel(headerDto.getPhone());
        lblPhoneValue.setFont(normalFont);
        headerPanel.add(lblPhoneValue, gbc);

        gbc.gridx = 2;
        gbc.gridy = row;
        JLabel lblTotalQty = new JLabel("Total Quantity:");
        lblTotalQty.setFont(boldFont);
        headerPanel.add(lblTotalQty, gbc);
        gbc.gridx = 3;
        JLabel lblTotalQtyValue = new JLabel(String.valueOf(headerDto.getTotalQty()));
        lblTotalQtyValue.setFont(normalFont);
        headerPanel.add(lblTotalQtyValue, gbc);

        gbc.gridx = 4;
        gbc.gridy = row;
        JLabel lblLoanDate = new JLabel("Loan Date:");
        lblLoanDate.setFont(boldFont);
        headerPanel.add(lblLoanDate, gbc);
        gbc.gridx = 5;
        JLabel lblLoanDateValue = new JLabel(FormatHelper.formatDate(headerDto.getLoanDt(), DateFormat.MMddyyyy));
        lblLoanDateValue.setFont(normalFont);
        headerPanel.add(lblLoanDateValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblReturnDate = new JLabel("Return Date:");
        lblReturnDate.setFont(boldFont);
        headerPanel.add(lblReturnDate, gbc);
        gbc.gridx = 1;
        JLabel lblReturnDateValue = new JLabel(FormatHelper.formatDate(headerDto.getLoanReturnDt(), DateFormat.MMddyyyy));
        lblReturnDateValue.setFont(normalFont);
        headerPanel.add(lblReturnDateValue, gbc);

        gbc.gridx = 2;
        gbc.gridy = row;
        JLabel lblCreatedBy = new JLabel("Created By:");
        lblCreatedBy.setFont(boldFont);
        headerPanel.add(lblCreatedBy, gbc);
        gbc.gridx = 3;
        JLabel lblCreatedByValue = new JLabel(headerDto.getCreatedBy());
        lblCreatedByValue.setFont(normalFont);
        headerPanel.add(lblCreatedByValue, gbc);

        gbc.gridx = 4;
        gbc.gridy = row;
        JLabel lblCreatedDate = new JLabel("Created Date:");
        lblCreatedDate.setFont(boldFont);
        headerPanel.add(lblCreatedDate, gbc);
        gbc.gridx = 5;
        JLabel lblCreatedDateValue = new JLabel(FormatHelper.formatDate(headerDto.getCreatedDt(), DateFormat.MMddyyyy));
        lblCreatedDateValue.setFont(normalFont);
        headerPanel.add(lblCreatedDateValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblUpdatedBy = new JLabel("Updated By:");
        lblUpdatedBy.setFont(boldFont);
        headerPanel.add(lblUpdatedBy, gbc);
        gbc.gridx = 1;
        JLabel lblUpdatedByValue = new JLabel(headerDto.getUpdateBy());
        lblUpdatedByValue.setFont(normalFont);
        headerPanel.add(lblUpdatedByValue, gbc);

        gbc.gridx = 2;
        gbc.gridy = row;
        JLabel lblUpdatedDate = new JLabel("Updated Date:");
        lblUpdatedDate.setFont(boldFont);
        headerPanel.add(lblUpdatedDate, gbc);
        gbc.gridx = 3;
        JLabel lblUpdatedDateValue = new JLabel(FormatHelper.formatDate(headerDto.getUpdateDt(), DateFormat.MMddyyyy));
        lblUpdatedDateValue.setFont(normalFont);
        headerPanel.add(lblUpdatedDateValue, gbc);

        gbc.gridx = 4;
        gbc.gridy = row;
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(boldFont);
        headerPanel.add(lblStatus, gbc);
        gbc.gridx = 5;
        JLabel lblStatusValue = new JLabel(headerDto.getStatusName());
        lblStatusValue.setFont(normalFont);
        headerPanel.add(lblStatusValue, gbc);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        String[] columnNames = {
            "Title", "Author", "Genre Category", "Publisher", "Publish Year"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable detailsTable = new JTable(tableModel);
        detailsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        detailsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsTable.setRowHeight(25);
        detailsTable.setShowGrid(true);
        detailsTable.setGridColor(Color.LIGHT_GRAY);

        for (TransactionLoanDetailDTO detail : detailsDto) {
            Object[] rowData = {
                detail.getTitle(),
                detail.getAuthor(),
                detail.getGenreCategory(),
                detail.getPublisher(),
                FormatHelper.formatDate(detail.getPublishYear(), DateFormat.MMddyyyy)
            };
            tableModel.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Loan Books"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                scrollPane.getBorder()
        ));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(separator, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Nút Revoke (hiển thị nếu statusName là "BORROW")
        if ("BORROW".equals(headerDto.getStatusName())) {
            btnRevoke = new JButton("Revoke");
            btnRevoke.setForeground(Color.BLUE);
            btnRevoke.setFocusPainted(false);
            btnRevoke.setOpaque(true);
            btnRevoke.setContentAreaFilled(true);
            btnRevoke.addActionListener(e -> revokeTransaction(headerDto));
            buttonPanel.add(btnRevoke);
        }

        // Nút Cancel
        btnClose = new JButton("Cancel");
        btnClose.setForeground(Color.RED);
        btnClose.setFocusPainted(false);
        btnClose.setOpaque(true);
        btnClose.setContentAreaFilled(true);
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private Void revokeTransaction(TransactionLoanHeaderDTO headerDto) {
        JOptionPane.showMessageDialog(this, "Revoke functionality is not implemented yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }
}