/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uit.librarymanagementapplication.mapper;

import com.uit.librarymanagementapplication.domain.DTO.Author.AuthorDTO;
import com.uit.librarymanagementapplication.domain.entity.Author;
import com.uit.librarymanagementapplication.domain.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author hieutruong
 */
@Mapper
public interface IAuthorMapper {

    IAuthorMapper INSTANCE = Mappers.getMapper(IAuthorMapper.class);

    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDTO authorDTO);

    List<AuthorDTO> toDTOList(List<Author> authors);

    List<Author> toEntityList(List<AuthorDTO> authorDTOs);
}
