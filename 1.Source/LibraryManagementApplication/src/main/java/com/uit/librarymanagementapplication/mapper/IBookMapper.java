package com.uit.librarymanagementapplication.mapper;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.entity.Book;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IBookMapper {

    IBookMapper INSTANCE = Mappers.getMapper(IBookMapper.class);

    List<BookDTO> toDTOList(List<Book> book);
}
