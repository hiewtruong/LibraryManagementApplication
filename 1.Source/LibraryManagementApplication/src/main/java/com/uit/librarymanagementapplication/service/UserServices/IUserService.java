package com.uit.librarymanagementapplication.service.UserServices;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import java.util.List;


public interface IUserService {
    UserDTO login(String username, String password, boolean isAdmin);

    UserDTO loginUser(String username,String password);

    List<UserRoleDTO> getAllUsers();
    
    List<UserRoleDTO> getAllUsersByKeyword(String keyword, String column);
    
    void createUser(UserRoleDTO user);
    
    void updateUser(UserRoleDTO user);
    
    void deleteUser(int userID);
    
    boolean checkDuplicateEmail(String email);

}
