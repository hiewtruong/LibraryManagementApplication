/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.controller;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.ITransactionLoanService;
import com.uit.librarymanagementapplication.service.TransactionLoanServices.TransactionLoanService;
import com.uit.librarymanagementapplication.view.admin.author.CreateAuthorModal;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.CreateTransactionLoanPanel;
import com.uit.librarymanagementapplication.view.admin.transactionLoan.TransactionLoanPanel;
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
            CreateTransactionLoanPanel createTranPanel = new CreateTransactionLoanPanel(contentPanel);
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
}
