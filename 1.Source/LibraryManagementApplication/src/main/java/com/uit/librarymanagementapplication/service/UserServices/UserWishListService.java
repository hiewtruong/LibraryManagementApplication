package com.uit.librarymanagementapplication.service.UserServices;

import com.uit.librarymanagementapplication.domain.DTO.User.UserWishListDTO;
import com.uit.librarymanagementapplication.domain.entity.UserWishList;
import com.uit.librarymanagementapplication.domain.repository.UserRepositories.UserWishListRepository;
import com.uit.librarymanagementapplication.mapper.IUserWishListMapper;

public class UserWishListService implements IUserWishListService {

    private final UserWishListRepository repository = new UserWishListRepository();

    @Override
    public void addToWishList(UserWishListDTO dto) {
        System.out.println("User ID" + dto.getUserID_FK());
        UserWishList wishList = IUserWishListMapper.INSTANCE.toEntity(dto);
       
        repository.addToWishList(wishList);
    }
}
