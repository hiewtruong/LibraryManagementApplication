/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.service.AuthorServices;

import com.uit.librarymanagementapplication.domain.DTO.Author.AuthorDTO;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface IAuthorService {
    
    List<AuthorDTO> getAuthorByName(String authorName);
}
