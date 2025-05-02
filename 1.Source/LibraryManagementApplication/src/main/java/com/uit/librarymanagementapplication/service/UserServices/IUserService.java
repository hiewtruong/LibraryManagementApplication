package com.uit.librarymanagementapplication.service.UserServices;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import java.util.List;

public interface IUserService {

    UserDTO login(String username, String password, boolean isAdmin);

    List<UserRoleDTO> getAllUsers();
}
