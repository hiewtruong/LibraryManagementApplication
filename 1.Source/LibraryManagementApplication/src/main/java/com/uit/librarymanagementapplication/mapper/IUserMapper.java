package com.uit.librarymanagementapplication.mapper;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
    
    UserDTO toDTO(UserRoleDTO user);
    
    User toEntity(UserRoleDTO userDTO);

}
