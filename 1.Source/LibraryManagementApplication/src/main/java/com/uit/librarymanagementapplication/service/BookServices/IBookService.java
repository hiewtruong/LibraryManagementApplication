/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.service.BookServices;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.DTO.Book.BookTransactionLoanDTO;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface IBookService {
    List<BookDTO> getAllBook();
    
    boolean updateBookQuantity(BookDTO book);
    
    List<BookTransactionLoanDTO> getAllBookTrans();
}
