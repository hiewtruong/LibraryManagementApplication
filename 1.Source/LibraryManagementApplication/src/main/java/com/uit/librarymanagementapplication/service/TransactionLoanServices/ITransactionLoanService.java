/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.service.TransactionLoanServices;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoanHeader.TransactionLoanHeaderDTO;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface ITransactionLoanService {
    List<TransactionLoanHeaderDTO> getAllTransLoanHeaderByKeyword(String keyword, String column);
}
