package com.uit.librarymanagementapplication.domain.DAO;

import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.User;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author phuvn
 */
public class UserDAO extends BaseDAO<User, Integer> {

    private static final String INSERT_SQL = "INSERT INTO Users (FirstName, LastName, UserName, Password, Gender, Email, Phone, Address, UserRoleID_FK, IsDelete, CreatedDt, CreatedBy, UpdateDt, UpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE Users SET FirstName = ?, LastName = ?, UserName = ?, Password = ?, Gender = ?, Email = ?, Phone = ?, Address = ?, UserRoleID_FK = ?, IsDelete = ?, CreatedDt = ?, CreatedBy = ?, UpdateDt = ?, UpdateBy = ? WHERE UserID = ?";
    private static final String DELETE_SQL = "DELETE FROM Users WHERE UserID = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM Users WHERE UserID = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM Users";

    @Override
    public void insert(User entity) {
        DbUtils.update(INSERT_SQL, mapEntityToParams(entity));
    }

    @Override
    public void update(User entity) {
        Object[] params = appendToArray(mapEntityToParams(entity), entity.getUserID());
        DbUtils.update(UPDATE_SQL, params);
    }

    @Override
    public void delete(Integer id) {
        DbUtils.update(DELETE_SQL, id);
    }

    @Override
    public User selectById(Integer id) {
        List<User> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<User> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<User> selectBySql(String sql, Object... args) {
        List<User> list = new java.util.ArrayList<>();
        try (ResultSet rs = DbUtils.query(sql, args)) {
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    protected User mapResultSetToEntity(ResultSet rs) throws Exception {
        return new User(
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
            rs.getDate("CreatedDt"),
            rs.getString("CreatedBy"),
            rs.getDate("UpdateDt"),
            rs.getString("UpdateBy")
        );
    }

    @Override
    protected Object[] mapEntityToParams(User entity) {
        return new Object[]{
            entity.getFirstName(),
            entity.getLastName(),
            entity.getUserName(),
            entity.getPassword(),
            entity.getGender(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getAddress(),
            entity.getUserRoleID(),
            entity.getIsDelete(),
            entity.getCreatedDt(),
            entity.getCreatedBy(),
            entity.getUpdateDt(),
            entity.getUpdateBy()
        };
    }

    public User getByUsername(String username) { // custom method to get user by username
        List<User> list = this.selectBySql("SELECT * FROM Users WHERE UserName = ?", username);
        return list.isEmpty() ? null : list.get(0);
    }
}
