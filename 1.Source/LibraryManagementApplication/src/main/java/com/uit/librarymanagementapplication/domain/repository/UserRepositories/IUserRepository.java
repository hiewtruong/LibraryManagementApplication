/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.UserRepositories;

import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.entity.User;

/**
 *
 * @author hieutruong
 */
public interface IUserRepository {
    UserRoleDTO findByUsername(String username);
    
    boolean createUser(User user);
}
