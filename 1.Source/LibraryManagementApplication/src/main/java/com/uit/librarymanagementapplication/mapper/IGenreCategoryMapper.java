/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.mapper;

import com.uit.librarymanagementapplication.domain.DTO.GenreCategory.GenreCategoryDTO;
import com.uit.librarymanagementapplication.domain.entity.GenreCategory;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author hieutruong
 */

@Mapper
public interface IGenreCategoryMapper {

    IGenreCategoryMapper INSTANCE = Mappers.getMapper(IGenreCategoryMapper.class);

    List<GenreCategoryDTO> toDTOList(List<GenreCategory> genreCategory);
}
