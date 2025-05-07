package com.uit.librarymanagementapplication.domain.DAO;

import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.Book;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author phuvn
 */
public class BookDAO extends BaseDAO<Book, Integer> {

    private static final String INSERT_SQL = "INSERT INTO Books (title, author, cover, landingPage, hashtag, genreCategory, publisher, publishYear, location, isDisplay, qtyOH, qtyAllocated, isDelete, createdDt, createdBy, updateDt, updateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE Books SET title = ?, author = ?, cover = ?, landingPage = ?, hashtag = ?, genreCategory = ?, publisher = ?, publishYear = ?, location = ?, isDisplay = ?, qtyOH = ?, qtyAllocated = ?, isDelete = ?, createdDt = ?, createdBy = ?, updateDt = ?, updateBy = ? WHERE BookID = ?";
    private static final String DELETE_SQL = "DELETE FROM Books WHERE BookID = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM Books WHERE BookID = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM Books";

    @Override
    public void insert(Book entity) {
        DbUtils.update(INSERT_SQL, mapEntityToParams(entity));
    }

    @Override
    public void update(Book entity) {
        Object[] params = appendToArray(mapEntityToParams(entity), entity.getBookID());
        DbUtils.update(UPDATE_SQL, params);
    }

    @Override
    public void delete(Integer id) {
        DbUtils.update(DELETE_SQL, id);
    }

    @Override
    public Book selectById(Integer id) {
        List<Book> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Book> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<Book> selectBySql(String sql, Object... args) {
        List<Book> list = new java.util.ArrayList<>();
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
    protected Book mapResultSetToEntity(ResultSet rs) throws Exception {
        return new Book(
            rs.getInt("BookID"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("cover"),
            rs.getString("landingPage"),
            rs.getString("hashtag"),
            rs.getString("genreCategory"),
            rs.getString("publisher"),
            rs.getDate("publishYear"),
            rs.getString("location"),
            rs.getInt("isDisplay"),
            rs.getInt("qtyOH"),
            rs.getInt("qtyAllocated"),
            rs.getInt("isDelete"),
            rs.getDate("createdDt"),
            rs.getString("createdBy"),
            rs.getDate("updateDt"),
            rs.getString("updateBy")
        );
    }

    @Override
    protected Object[] mapEntityToParams(Book entity) {
        return new Object[]{
            entity.getTitle(),
            entity.getAuthor(),
            entity.getCover(),
            entity.getLandingPage(),
            entity.getHashtag(),
            entity.getGenreCategory(),
            entity.getPublisher(),
            entity.getPublishYear(),
            entity.getLocation(),
            entity.getIsDisplay(),
            entity.getQtyOH(),
            entity.getQtyAllocated(),
            entity.getIsDelete(),
            entity.getCreatedDt(),
            entity.getCreatedBy(),
            entity.getUpdateDt(),
            entity.getUpdateBy()
        };
    }

    public Book getByTitle(String title) {
        List<Book> list = this.selectBySql("SELECT * FROM Books WHERE title = ?", title);
        return list.isEmpty() ? null : list.get(0);
    }
}
