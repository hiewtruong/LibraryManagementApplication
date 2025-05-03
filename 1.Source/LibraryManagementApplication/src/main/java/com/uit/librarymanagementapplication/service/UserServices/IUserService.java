package com.uit.librarymanagementapplication.service.UserServices;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;


public interface IUserService {
    UserDTO login(String username, String password, boolean isAdmin);
    UserDTO loginUser(String username,String password);
}
