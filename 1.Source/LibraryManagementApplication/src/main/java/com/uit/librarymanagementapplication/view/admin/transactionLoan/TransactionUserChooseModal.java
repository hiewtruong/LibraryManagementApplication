/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.view.admin.transactionLoan;

import com.uit.librarymanagementapplication.controller.TransacitonLoanController;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.view.lib.CommonUI;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TransactionUserChooseModal extends JDialog {

    private TransacitonLoanController controller = new TransacitonLoanController();
    private JPanel contentPanel;
    private JTable userTable;
    private UserRoleDTO selectedUser;

    public TransactionUserChooseModal(JFrame parent, JPanel contentPanel, List<UserRoleDTO> users) {
        super(parent, "Choose User", true);
        this.contentPanel = contentPanel;
        this.selectedUser = null;
        initUI(users);
        setLocationRelativeTo(parent);
        setSize(700, 300);
        setResizable(false);
    }

    private void initUI(List<UserRoleDTO> users) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(242, 242, 242));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(242, 242, 242));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 30, 15, 30),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Choose User"),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        ));

        String[] columns = {"User ID", "User Name", "Email"};
        DefaultTableModel userModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(userModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        userTable.setRowHeight(25);
        userTable.setShowGrid(true);
        userTable.setGridColor(Color.LIGHT_GRAY);
        userTable.setBackground(new Color(242, 242, 242));
        userTable.setFocusable(false);
        userTable.setBorder(null);

        for (UserRoleDTO user : users) {
            Object[] rowData = {
                user.getUserID(),
                user.getUserName(),
                user.getEmail()
            };
            userModel.addRow(rowData);
        }

        JScrollPane userScrollPane = new JScrollPane(userTable);
        userScrollPane.setBackground(new Color(242, 242, 242));
        userScrollPane.getViewport().setBackground(new Color(242, 242, 242));
        userScrollPane.setBorder(null);
        headerPanel.add(userScrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(242, 242, 242));
        mainPanel.add(headerPanel, BorderLayout.CENTER);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(separator, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);


        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(242, 242, 242));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));

        JButton btnSelect = new JButton("Select");
        btnSelect.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSelect.setPreferredSize(new Dimension(80, 30));
        btnSelect.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                selectedUser = new UserRoleDTO();
                selectedUser.setUserID((int) userTable.getValueAt(selectedRow, 0));
                selectedUser.setUserName((String) userTable.getValueAt(selectedRow, 1));
                selectedUser.setEmail((String) userTable.getValueAt(selectedRow, 2));
                dispose();
            } else {
                CommonUI.showAlerValidate(this, "Please select a user!");
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancel.setForeground(Color.RED);
        btnCancel.setPreferredSize(new Dimension(80, 30));
        btnCancel.addActionListener(e -> dispose());

        footerPanel.add(btnSelect);
        footerPanel.add(btnCancel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public UserRoleDTO getSelectedUser() {
        return selectedUser;
    }
}