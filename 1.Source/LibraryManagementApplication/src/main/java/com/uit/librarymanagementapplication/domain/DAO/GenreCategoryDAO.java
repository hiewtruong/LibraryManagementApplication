package com.uit.librarymanagementapplication.domain.DAO;

import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.GenreCategory;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author phuvn
 */
public class GenreCategoryDAO extends BaseDAO<GenreCategory, Integer> {

    private static final String INSERT_SQL = "INSERT INTO GenreCategories (nameCategory, genreCategory, isDelete, createdDt, createdBy, updateDt, updateBy) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE GenreCategories SET nameCategory = ?, genreCategory = ?, isDelete = ?, createdDt = ?, createdBy = ?, updateDt = ?, updateBy = ? WHERE genreCategoryID = ?";
    private static final String DELETE_SQL = "DELETE FROM GenreCategories WHERE genreCategoryID = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM GenreCategories WHERE genreCategoryID = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM GenreCategories WHERE IsDelete = 0";

    @Override
    public void insert(GenreCategory entity) {
        DbUtils.update(INSERT_SQL, mapEntityToParams(entity));
    }

    @Override
    public void update(GenreCategory entity) {
        Object[] params = appendToArray(mapEntityToParams(entity), entity.getGenreCategoryID());
        DbUtils.update(UPDATE_SQL, params);
    }

    @Override
    public void delete(Integer id) {
        DbUtils.update(DELETE_SQL, id);
    }

    @Override
    public GenreCategory selectById(Integer id) {
        List<GenreCategory> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<GenreCategory> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<GenreCategory> selectBySql(String sql, Object... args) {
        List<GenreCategory> list = new java.util.ArrayList<>();
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
    protected GenreCategory mapResultSetToEntity(ResultSet rs) throws Exception {
        return new GenreCategory(
            rs.getInt("genreCategoryID"),
            rs.getString("nameCategory"),
            rs.getString("genreCategory"),
            rs.getInt("isDelete"),
            rs.getDate("createdDt"),
            rs.getString("createdBy"),
            rs.getDate("updateDt"),
            rs.getString("updateBy")
        );
    }

    @Override
    protected Object[] mapEntityToParams(GenreCategory entity) {
        return new Object[]{
            entity.getNameCategory(),
            entity.getGenreCategory(),
            entity.getIsDelete(),
            entity.getCreatedDt(),
            entity.getCreatedBy(),
            entity.getUpdateDt(),
            entity.getUpdateBy()
        };
    }

    public GenreCategory getByNamCategory(String nameCategory) {
        List<GenreCategory> list = this.selectBySql("SELECT * FROM GenreCategories WHERE NameCategory = ?", nameCategory);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<GenreCategory> getByBookCategories(String bookCategory) {
        // bookCategory type is: "Id1, Id2, Id3"
        String sql = "SELECT * FROM GenreCategories WHERE GenreCategoryID IN (" + bookCategory + ") AND IsDelete = 0";
        if (bookCategory == null || bookCategory.isEmpty()) {
            sql = "SELECT * FROM GenreCategories WHERE IsDelete = 0";
        }
        List<GenreCategory> list = this.selectBySql(sql);
        return list.isEmpty() ? null : list;
    }
}
