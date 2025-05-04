package com.uit.librarymanagementapplication.service.BookServices;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.DTO.Book.BookTransactionLoanDTO;
import java.util.List;


public interface IBookService {
    List<BookDTO> getAllBook();
    
        boolean updateBookQuantity(BookDTO book);
    
    List<BookTransactionLoanDTO> getAllBookTrans();

}
