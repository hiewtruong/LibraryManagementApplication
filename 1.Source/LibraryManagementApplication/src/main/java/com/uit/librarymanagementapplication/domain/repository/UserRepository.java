/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository;

import com.uit.librarymanagementapplication.domain.DTO.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hieutruong
 */
public class UserRepository implements IUserRepository {

    private static UserRepository instance;

    public UserRepository() {

    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    @Override
    public UserRoleDTO findByUsername(String username) {
        UserRoleDTO user = null;
        String sql = "SELECT u.*, r.RoleName, r.IsAdmin "
                + "FROM [dbo].[Users] u "
                + "JOIN [dbo].[UserRoles] r ON u.UserRoleID_FK = r.UserRoleID "
                + "WHERE u.UserName = ? AND u.IsDelete = 0";
        try (
                Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new UserRoleDTO(
                            rs.getInt("UserID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getInt("Gender"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getInt("UserRoleID_FK"),
                            rs.getInt("IsDelete"),
                            rs.getInt("IsAdmin"),
                            rs.getString("RoleName")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
