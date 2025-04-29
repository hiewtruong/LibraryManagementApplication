/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.view.admin.transactionLoan;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author hieutruong
 */
public class CreateTransactionLoanPanel extends JPanel{
      public CreateTransactionLoanPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Tạo đơn thuê/mượn sách"));
        JLabel label = new JLabel("Nội dung tạo đơn thuê/mượn sách", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}