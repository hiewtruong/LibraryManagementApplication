/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uit.librarymanagementapplication.domain.repository.BookRepositories;

/**
 *
 * @author Admin
 */
import com.uit.librarymanagementapplication.domain.entity.Book;
import java.util.List;

public interface IBookRepository {
    List<Book> findAllBooks();
    List<Book> findByTitile(String title);
    Book findBookById(int bookID);
    boolean createBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(int bookID);
    void updateBookQuantity(int bookId, int qtyOH, int qtyAllocated);
}