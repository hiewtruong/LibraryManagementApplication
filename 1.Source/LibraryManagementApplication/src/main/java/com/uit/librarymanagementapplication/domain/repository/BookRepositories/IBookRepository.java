/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.BookRepositories;

import com.uit.librarymanagementapplication.domain.entity.Book;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface IBookRepository {
     List<Book> getAllBooks();
}
