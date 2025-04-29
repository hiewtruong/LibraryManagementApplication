/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.AuthorRepositories;

import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.entity.Author;
import com.uit.librarymanagementapplication.domain.entity.User;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface IAuthorRepository {
    
    List<Author> getAllAuthors();
    
    List<Author> getAuthorsByName(String keyword);
    
    boolean createAuthor(Author author);
}
