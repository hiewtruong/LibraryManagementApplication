package com.uit.librarymanagementapplication.mapper;

import com.uit.librarymanagementapplication.domain.DTO.UserDTO;
import com.uit.librarymanagementapplication.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
