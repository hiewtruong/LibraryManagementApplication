package com.uit.librarymanagementapplication.mapper;

import com.uit.librarymanagementapplication.domain.DTO.User.UserWishListDTO;
import com.uit.librarymanagementapplication.domain.entity.UserWishList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserWishListMapper {
    IUserWishListMapper INSTANCE = Mappers.getMapper(IUserWishListMapper.class);
    
    @Mapping(source = "userID_FK", target = "userID")
    @Mapping(source = "bookID_FK", target = "bookID")
    @Mapping(target = "createdDt", expression = "java(new java.util.Date())")
    @Mapping(target = "updateDt", expression = "java(new java.util.Date())")
    UserWishList toEntity(UserWishListDTO dto);
}