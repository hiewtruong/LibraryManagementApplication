/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.service.GenreCategoryServices;

import com.uit.librarymanagementapplication.domain.DTO.Author.AuthorDTO;
import com.uit.librarymanagementapplication.domain.DTO.GenreCategory.GenreCategoryDTO;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface IGenreCategoryService {
    List<GenreCategoryDTO> getAllGenreCategory ();
}
