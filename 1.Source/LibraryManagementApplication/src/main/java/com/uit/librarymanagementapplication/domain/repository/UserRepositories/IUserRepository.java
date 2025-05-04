/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.UserRepositories;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.entity.User;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public interface IUserRepository {

    UserRoleDTO findByUsername(String username);
    UserDTO findUserByUsernameAndPassword(String username);
    boolean createUser(User user);

    List<UserRoleDTO> getAllUsersCustomer();
    
    List<UserRoleDTO> getAllUsersCustomerBySearch(String keyword, String column);
    
    User getUser(int userID);
    
    boolean checkUniqEmail(String email);
    
    boolean updateUser(User user);
    
    boolean deleteUser(int userID);
}
