/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.GenreCategoryRepositories;

import com.uit.librarymanagementapplication.domain.entity.GenreCategory;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface IGenreCategoryRepository {
    List<GenreCategory> getAllCategories();
}
