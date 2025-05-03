/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uit.librarymanagementapplication.service.BookServices;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import java.util.List;


public interface iBookService {
    List<BookDTO>getAllBook();
}
