package com.uit.librarymanagementapplication.domain.repository.AuthorRepositories;

import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.Author;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AuthorRepository implements IAuthorRepository {

    private static AuthorRepository instance;

    public AuthorRepository() {

    }

    public static AuthorRepository getInstance() {
        if (instance == null) {
            instance = new AuthorRepository();
        }
        return instance;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Authors] WHERE IsDelete = 0 ORDER BY AuthorID desc";

        try (
                Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Author author = new Author();
                author.setAuthorID(rs.getInt("AuthorID"));
                author.setAuthorName(rs.getString("AuthorName"));
                author.setIsDelete(rs.getInt("IsDelete"));
                author.setCreatedDt(rs.getTimestamp("CreatedDt"));
                author.setCreatedBy(rs.getString("CreatedBy"));
                author.setUpdateDt(rs.getTimestamp("UpdateDt"));
                author.setUpdateBy(rs.getString("UpdateBy"));
                authors.add(author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close();
        }
        return authors;
    }

    @Override
    public boolean createAuthor(Author author) {
        boolean isSuccess = false;
        String sql = "INSERT INTO [dbo].[Authors] (AuthorName, IsDelete, CreatedDt, CreatedBy, UpdateDt, UpdateBy) "
                + "VALUES (?, 0, GETDATE(), ?, GETDATE(), ?)";

        try {
            DbUtils.beginTransaction();
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, author.getAuthorName());
                stmt.setString(2, author.getCreatedBy());
                stmt.setString(3, author.getCreatedBy());
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    DbUtils.commit();
                    isSuccess = true;
                } else {
                    DbUtils.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            DbUtils.rollback();
        } finally {
            DbUtils.close();
        }
        return isSuccess;
    }

    @Override
    public List<Author> getAuthorsByName(String keyword) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Authors] WHERE AuthorName LIKE ? AND IsDelete = 0 ORDER BY AuthorID desc";

        try (
            Connection conn = DbUtils.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setAuthorID(rs.getInt("AuthorID"));
                    author.setAuthorName(rs.getString("AuthorName"));
                    author.setIsDelete(rs.getInt("IsDelete"));
                    author.setCreatedDt(rs.getTimestamp("CreatedDt"));
                    author.setCreatedBy(rs.getString("CreatedBy"));
                    author.setUpdateDt(rs.getTimestamp("UpdateDt"));
                    author.setUpdateBy(rs.getString("UpdateBy"));
                    authors.add(author);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close();
        }
        return authors;
    }
}
