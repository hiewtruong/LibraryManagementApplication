/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository;

import com.uit.librarymanagementapplication.domain.model.User;

/**
 *
 * @author hieutruong
 */
public interface IUserRepository {
    User findByUsername(String username);
}
