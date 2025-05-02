/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.controller;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.service.BookServices.BookService;
import com.uit.librarymanagementapplication.service.BookServices.IBookService;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.ITransactionLoanService;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.TransactionLoanService;
import com.uit.librarymanagementapplication.service.UserServices.IUserService;
import com.uit.librarymanagementapplication.service.UserServices.UserService;
import com.uit.librarymanagementapplication.view.admin.author.CreateAuthorModal;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.CreateTransactionLoanPanel;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.TransactionLoanPanel;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.TransactionUserChooseModal;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.ViewTransactionLoanDetailModal;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author hieutruong
 */
public class TransacitonLoanController {

    ITransactionLoanService transService = TransactionLoanService.getInstance();
    IUserService userSerivce = UserService.getInstance();
    IBookService bookService = BookService.getInstance();

    public TransacitonLoanController() {
    }

    public void Init(JPanel contentPanel, boolean forceReload) {
        if (forceReload || !(contentPanel.getComponentCount() > 0 && contentPanel.getComponent(0) instanceof TransactionLoanPanel)) {
            contentPanel.removeAll();
            List<TransactionLoanHeaderDTO> result = transService.getAllTransLoanHeaderByKeyword("", "");
            TransactionLoanPanel tranPanel = new TransactionLoanPanel(result, contentPanel);
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(tranPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public void CreateTransactionLoan(JPanel contentPanel, boolean forceReload) {
        if (forceReload || !(contentPanel.getComponentCount() > 0 && contentPanel.getComponent(0) instanceof CreateTransactionLoanPanel)) {
            contentPanel.removeAll();
            List<UserRoleDTO> users = userSerivce.getAllUsers();
            List<BookDTO> books = bookService.getAllBook();
            CreateTransactionLoanPanel createTranPanel = new CreateTransactionLoanPanel(contentPanel, users, books);
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(createTranPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public void ViewTransactionLoan(JFrame parentFrame, JPanel contentPanel, TransactionLoanHeaderDTO headerDto) {
        List<TransactionLoanDetailDTO> details = transService.getAllTransDetails(headerDto.getLoanHeaderID());
        ViewTransactionLoanDetailModal dialog = new ViewTransactionLoanDetailModal(parentFrame, contentPanel, headerDto, details);
        dialog.setVisible(true);
    }

    public void ChooseUserTransactionLoan(JFrame parentFrame, JPanel contentPanel, List<UserRoleDTO> users) {
        TransactionUserChooseModal dialog = new TransactionUserChooseModal(parentFrame, contentPanel, users);
        dialog.setVisible(true);
    }
}
