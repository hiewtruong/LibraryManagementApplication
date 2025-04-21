package com.uit.librarymanagementapplication.service;

import com.uit.librarymanagementapplication.domain.DTO.UserDTO;


public interface IUserService {
    UserDTO login(String username, String password, boolean isAdmin);
}
