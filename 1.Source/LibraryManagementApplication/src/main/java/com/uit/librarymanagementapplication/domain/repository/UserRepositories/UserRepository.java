package com.uit.librarymanagementapplication.domain.repository.UserRepositories;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            stmt.setString(10, user.getCreatedBy());
            stmt.setString(11, user.getUpdateBy());
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

    @Override
    public UserDTO findUserByUsernameAndPassword(String username) {
        UserDTO user = null;
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.UserName, u.Password, u.Email, u.Phone, u.Address, u.IsDelete, r.IsAdmin, r.RoleName\n"
                + "FROM [dbo].[Users] u\n"
                + "JOIN [dbo].[UserRoles] r ON u.UserRoleID_FK = r.UserRoleID\n"
                + "WHERE u.UserName = ? AND u.IsDelete = 0";

        try (
                Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new UserDTO(
                            rs.getInt("UserID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
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
    public List<UserRoleDTO> getAllUsersCustomerBySearch(String keyword, String column) {
        List<UserRoleDTO> userList = new ArrayList<>();

        String sql = """
            SELECT u.*, r.RoleName, r.IsAdmin
            FROM [dbo].[Users] u
            JOIN [dbo].[UserRoles] r ON u.UserRoleID_FK = r.UserRoleID
            WHERE u.IsDelete = 0 AND r.IsAdmin = 0
            AND %s LIKE ?
            ORDER BY u.UserID DESC
        """;

        sql = String.format(sql, column);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                UserRoleDTO user = new UserRoleDTO(
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
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage(), e);
        } finally {
            DbUtils.close();
        }
        return userList;
    }

    @Override
    public User getUser(int userID) {
        User user = null;

        String sql = """
            SELECT u.UserID, u.FirstName, u.LastName, u.UserName, u.Password, u.Gender, 
                   u.Email, u.Phone, u.Address, u.UserRoleID_FK, u.IsDelete, 
                   u.CreatedDt, u.CreatedBy, u.UpdateDt, u.UpdateBy
            FROM [dbo].[Users] u
            WHERE u.UserID = ? AND u.IsDelete = 0
        """;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setUserName(rs.getString("UserName"));
                user.setPassword(rs.getString("Password"));
                user.setGender(rs.getInt("Gender"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setAddress(rs.getString("Address"));
                user.setUserRoleID(rs.getInt("UserRoleID_FK"));
                user.setIsDelete(rs.getInt("IsDelete"));
                user.setCreatedDt(rs.getDate("CreatedDt"));
                user.setCreatedBy(rs.getString("CreatedBy"));
                user.setUpdateDt(rs.getDate("UpdateDt"));
                user.setUpdateBy(rs.getString("UpdateBy"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user: " + e.getMessage(), e);
        } finally {
            DbUtils.close();
        }

        return user;
    }

    @Override
    public boolean checkUniqEmail(String email) {
        boolean isUnique = true;

        String sql = """
            SELECT COUNT(*) 
            FROM [dbo].[Users] 
            WHERE Email = ? AND IsDelete = 0
        """;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                isUnique = (count == 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking email uniqueness: " + e.getMessage(), e);
        } finally {
            DbUtils.close();
        }

        return isUnique;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = """
            UPDATE [dbo].[Users]
            SET FirstName = ?, LastName = ?, UserName = ?, Gender = ?, 
                Email = ?, Phone = ?, Address = ?, 
                UpdateDt = ?, UpdateBy = ?
            WHERE UserID = ? AND IsDelete = 0
        """;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DbUtils.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUserName());
            stmt.setInt(4, user.getGender());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getAddress());
            stmt.setDate(8, new Date(user.getUpdateDt().getTime()));
            stmt.setString(9, user.getUpdateBy());
            stmt.setInt(10, user.getUserID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        } finally {
            DbUtils.close();
        }
    }

    @Override
    public boolean deleteUser(int userID) {
        String sql = """
            UPDATE [dbo].[Users]
            SET IsDelete = 1
            WHERE UserID = ? AND IsDelete = 0
        """;

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DbUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        } finally {
            DbUtils.close();
        }
    }

    @Override
    public List<UserRoleDTO> getAllUsersCustomer() {
        List<UserRoleDTO> userList = new ArrayList<>();

        String sql = """
            SELECT u.*, r.RoleName, r.IsAdmin
            FROM [dbo].[Users] u
            JOIN [dbo].[UserRoles] r ON u.UserRoleID_FK = r.UserRoleID
            WHERE u.IsDelete = 0 AND r.IsAdmin = 0
            ORDER BY u.UserID DESC
        """;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                UserRoleDTO user = new UserRoleDTO(
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
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage(), e);
        } finally {
            DbUtils.close();
        }
        return userList;
    }
}
