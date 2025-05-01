/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.TransactionLoanDetailRepositories;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface ITransactionLoanDetailRepository {
    List<TransactionLoanDetailDTO> gettTransactionLoanByHeaderID(int loanHeaderID);
}
