package com.uit.librarymanagementapplication.view.admin.user;

import com.uit.librarymanagementapplication.controller.UserController;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.service.UserServices.IUserService;
import com.uit.librarymanagementapplication.service.UserServices.UserService;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPanel extends JPanel {

    IUserService userService = UserService.getInstance();
    UserController controller = new UserController();
    private JPanel contentPanel;

    private JTextField searchField;
    private JButton searchButton;
    private JButton createButton;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JComboBox<String> searchColumnComboBox;
    private Map<String, String> columnMapping;

    public UserPanel(List<UserRoleDTO> users, JPanel contentPanel) {
        this.contentPanel = contentPanel;
        initUI(users);
    }

    private void initUI(List<UserRoleDTO> users) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Management User"),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)
        ));

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        columnMapping = new HashMap<>();
        columnMapping.put("FirstName", "u.FirstName");
        columnMapping.put("LastName", "u.LastName");
        columnMapping.put("UserName", "u.UserName");
        columnMapping.put("Email", "u.Email");
        columnMapping.put("Phone", "u.Phone");

        String[] columnDisplayNames = {"FirstName", "LastName", "UserName", "Email", "Phone"};
        searchColumnComboBox = new JComboBox<>(columnDisplayNames);
        searchColumnComboBox.setSelectedItem("UserName");
        searchColumnComboBox.setPreferredSize(new Dimension(250, searchColumnComboBox.getPreferredSize().height));

        JLabel searchLabel = new JLabel("Find:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchUsers());

        leftPanel.add(searchLabel);
        leftPanel.add(searchColumnComboBox);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        topPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createButton = new JButton("Create");
        createButton.addActionListener(e -> createNewUser());
        rightPanel.add(createButton);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {
            "Edit", "User ID", "First Name", "Last Name", "User Name", "Email", "Phone",
            "Gender", "Address"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(tableModel);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        userTable.getTableHeader().setReorderingAllowed(false);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.setRowHeight(35);
        userTable.setShowGrid(true);
        userTable.setGridColor(Color.BLACK);

        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setBorder(null);
        for (int i = 0; i < userTable.getColumnCount(); i++) {
            if (i == 0) {
                userTable.getColumnModel().getColumn(i).setCellRenderer(new HyperlinkRenderer());
            } else {
                userTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = userTable.columnAtPoint(e.getPoint());
                int row = userTable.rowAtPoint(e.getPoint());
                if (column == 0) {
                    UserRoleDTO user = users.get(row);
                    controller.UpdateUser(parentFrame, contentPanel, user);
                }
            }
        });

        userTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        userTable.getColumnModel().getColumn(0).setMinWidth(100);
        userTable.getColumnModel().getColumn(0).setMaxWidth(100);

        userTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        userTable.getColumnModel().getColumn(1).setMinWidth(100);
        userTable.getColumnModel().getColumn(1).setMaxWidth(100);

        userTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        userTable.getColumnModel().getColumn(2).setMinWidth(150);
        userTable.getColumnModel().getColumn(2).setMaxWidth(150);

        userTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        userTable.getColumnModel().getColumn(3).setMinWidth(150);
        userTable.getColumnModel().getColumn(3).setMaxWidth(150);

        userTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        userTable.getColumnModel().getColumn(4).setMinWidth(150);
        userTable.getColumnModel().getColumn(4).setMaxWidth(150);

        userTable.getColumnModel().getColumn(5).setPreferredWidth(250);
        userTable.getColumnModel().getColumn(5).setMinWidth(250);
        userTable.getColumnModel().getColumn(5).setMaxWidth(250);

        userTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        userTable.getColumnModel().getColumn(6).setMinWidth(120);
        userTable.getColumnModel().getColumn(6).setMaxWidth(120);

        userTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        userTable.getColumnModel().getColumn(7).setMinWidth(80);
        userTable.getColumnModel().getColumn(7).setMaxWidth(80);

        userTable.getColumnModel().getColumn(8).setPreferredWidth(200);
        userTable.getColumnModel().getColumn(8).setMinWidth(200);
        userTable.getColumnModel().getColumn(8).setMaxWidth(200);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        userTable.setPreferredScrollableViewportSize(new Dimension(
                userTable.getPreferredScrollableViewportSize().width,
                userTable.getRowHeight() * 10
        ));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: 0");
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bottomPanel.add(totalLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        loadUserData(users);
    }

    private void loadUserData(List<UserRoleDTO> users) {
        tableModel.setRowCount(0);

        for (UserRoleDTO user : users) {
            Object[] row = {
                "Edit",
                user.getUserID(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                user.getPhone(),
                user.getGender() == 1 ? "Male" : "Female",
                user.getAddress()
            };
            tableModel.addRow(row);
        }
        totalLabel.setText("Total: " + users.size());
    }

    private void searchUsers() {
        String keyword = searchField.getText().trim();
        String selectedColumnDisplay = (String) searchColumnComboBox.getSelectedItem();
        String selectedColumn = columnMapping.get(selectedColumnDisplay);
        List<UserRoleDTO> users = userService.getAllUsersByKeyword(keyword, selectedColumn);
        loadUserData(users);
    }

    private void createNewUser() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        controller.CreateUser(parentFrame, contentPanel);
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
