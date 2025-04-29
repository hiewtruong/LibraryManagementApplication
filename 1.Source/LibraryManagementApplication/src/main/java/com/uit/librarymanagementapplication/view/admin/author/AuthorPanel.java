package com.uit.librarymanagementapplication.view.admin.author;

import com.uit.librarymanagementapplication.controller.AuthorController;
import com.uit.librarymanagementapplication.domain.DTO.Author.AuthorDTO;
import com.uit.librarymanagementapplication.lib.Constants.DateFormat;
import com.uit.librarymanagementapplication.lib.FormatHelper;
import com.uit.librarymanagementapplication.service.AuthorServices.AuthorService;
import com.uit.librarymanagementapplication.service.AuthorServices.IAuthorService;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AuthorPanel extends JPanel {

    IAuthorService authorService = AuthorService.getInstance();
    AuthorController controller = new AuthorController();
    private JPanel contentPanel;

    private JTextField searchField;
    private JButton searchButton;
    private JButton createButton;
    private JTable authorTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public AuthorPanel(List<AuthorDTO> authors, JPanel contentPanel) {
        initUI(authors);
        this.contentPanel = contentPanel;
    }

    private void initUI(List<AuthorDTO> authors) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Management Author"));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Management Author"),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)
        ));

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Find:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchAuthors());
        leftPanel.add(searchLabel);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        topPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createButton = new JButton("Create");
        createButton.addActionListener(e -> createNewAuthor());
        rightPanel.add(createButton);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Author Name", "CreatedDt", "CreatedBy", "UpdateDt", "UpdatedBy"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        authorTable = new JTable(tableModel);
        authorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        authorTable.getTableHeader().setReorderingAllowed(false);
        authorTable.setFont(new Font("Arial", Font.PLAIN, 14));
        authorTable.setRowHeight(35);
        authorTable.setShowGrid(true);
        authorTable.setGridColor(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(authorTable);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: 0");
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bottomPanel.add(totalLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        loadAuthorData(authors);
    }

    private void loadAuthorData(List<AuthorDTO> authors) {
        tableModel.setRowCount(0);

        for (AuthorDTO author : authors) {
            Object[] row = {
                author.getAuthorName(),
                FormatHelper.formatDate(author.getCreatedDt(), DateFormat.MMddyyyy),
                author.getCreatedBy(),
                FormatHelper.formatDate(author.getUpdateDt(), DateFormat.MMddyyyy),
                author.getUpdateBy()
            };
            tableModel.addRow(row);
        }
        totalLabel.setText("Total: " + authors.size());
    }

    private void searchAuthors() {
        String keyword = searchField.getText().trim();
        List<AuthorDTO> authors = authorService.getAuthorByName(keyword);
        loadAuthorData(authors);
    }

    private void createNewAuthor() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        controller.CreateAuthor(parentFrame, contentPanel);
    }
}
