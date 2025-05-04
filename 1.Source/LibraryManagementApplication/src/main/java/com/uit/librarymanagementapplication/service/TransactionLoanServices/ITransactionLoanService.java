/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.service.TransactionLoanServices;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderRequestDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderRevokeDTO;
import com.uit.librarymanagementapplication.domain.entity.TransactionLoanHeader;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface ITransactionLoanService {
    List<TransactionLoanHeaderDTO> getAllTransLoanHeaderByKeyword(String keyword, String column);
    
    List<TransactionLoanDetailDTO> getAllTransDetails(int loanHeaderID);
    
    void createTransactionLoan(TransactionLoanHeaderRequestDTO request);
    
    void revokeTransactionLoan(TransactionLoanHeaderRevokeDTO request);
   
}
