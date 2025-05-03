/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uit.librarymanagementapplication.service.UserServices;

import com.uit.librarymanagementapplication.domain.DTO.User.UserWishListDTO;


public interface IUserWishListService {
     void addToWishList(UserWishListDTO dto);
}
