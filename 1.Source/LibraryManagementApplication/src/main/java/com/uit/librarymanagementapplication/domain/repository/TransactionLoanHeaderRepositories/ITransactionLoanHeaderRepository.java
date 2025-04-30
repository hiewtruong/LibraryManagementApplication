/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoanHeader.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.entity.Author;
import java.util.List;

/**
 *
 * @author Hieu.Truong
 */
public interface ITransactionLoanHeaderRepository {
    
    List<TransactionLoanHeaderDTO> getAllTransHeader();
}
