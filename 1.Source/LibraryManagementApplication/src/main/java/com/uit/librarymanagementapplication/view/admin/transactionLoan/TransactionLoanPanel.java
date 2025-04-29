package com.uit.librarymanagementapplication.view.admin.transactionLoan;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hieutruong
 */
public class TransactionLoanPanel extends JPanel{
      public TransactionLoanPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Quản lý Đơn thuê/mượn sách"));
        JLabel label = new JLabel("Nội dung quản lý đơn thuê/mượn sách", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
