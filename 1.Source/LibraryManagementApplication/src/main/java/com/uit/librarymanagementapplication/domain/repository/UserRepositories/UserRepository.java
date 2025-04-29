/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.UserRepositories;

import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.User;
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

    @Override
    public boolean createUser(User user) {
        String sql = "INSERT INTO [dbo].[Users] "
                + "(FirstName, LastName, UserName, Password, Gender, Email, Phone, Address, UserRoleID_FK, IsDelete, CreatedDt, CreatedBy, UpdateDt, UpdateBy) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, GETDATE(), ?, GETDATE(), ?)";

        try {
            DbUtils.beginTransaction();

            Connection conn = DbUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUserName());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getGender());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getPhone());
            stmt.setString(8, user.getAddress());
            stmt.setInt(9, user.getUserRoleID());
            stmt.setString(10, "admin");
            stmt.setString(11, "admin");
            int rows = stmt.executeUpdate();
            DbUtils.commit();
            return rows > 0;

        } catch (SQLException e) {
            DbUtils.rollback();
            e.printStackTrace();
        } finally {
            DbUtils.close();
        }
        return false;
    }

}
