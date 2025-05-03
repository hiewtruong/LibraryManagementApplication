package com.uit.librarymanagementapplication.domain.repository.UserRepositories;

import com.uit.librarymanagementapplication.domain.entity.UserWishList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.uit.librarymanagementapplication.domain.DbUtils;
public class UserWishListRepository {

    public void addToWishList(UserWishList wishList) {
        String sql = "INSERT INTO UserWishLists (UserID_FK, BookID_FK, IsDelete, CreatedDt, CreatedBy, UpdateDt, UpdateBy) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wishList.getUserID());
            stmt.setInt(2, wishList.getBookID());
            stmt.setInt(3, wishList.getIsDelete());
            stmt.setDate(4, new java.sql.Date(wishList.getCreatedDt().getTime()));
            stmt.setString(5, wishList.getCreatedBy());
            stmt.setDate(6, new java.sql.Date(wishList.getUpdateDt().getTime()));
            stmt.setString(7, wishList.getUpdateBy());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}