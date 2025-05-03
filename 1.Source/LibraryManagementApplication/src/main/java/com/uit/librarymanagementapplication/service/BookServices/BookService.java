/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uit.librarymanagementapplication.service.BookServices;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.entity.Book;
import com.uit.librarymanagementapplication.domain.repository.BookRepositories.BookRepository;
import com.uit.librarymanagementapplication.mapper.IBookMapper;
import java.util.List;
import java.util.stream.Collectors;


public class BookService implements iBookService{
  private final BookRepository bookRepository = new BookRepository();
    @Override
    public List<BookDTO> getAllBook() {
          List<Book> books = bookRepository.findAllBooks();
        return books.stream()
                .map(IBookMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
      public boolean updateBookQuantity(BookDTO book) {
        if (book.getQtyOH() > 0) {
           
            book.setQtyOH(book.getQtyOH() - 1);
            book.setQtyAllocated(book.getQtyAllocated() + 1);

           
            bookRepository.updateBookQuantity(book.getBookID(), book.getQtyOH(), book.getQtyAllocated());
            return true;
        } else {
       
            return false;
        }
    }
    
}
