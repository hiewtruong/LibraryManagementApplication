package com.uit.librarymanagementapplication.domain.repository.GenreCategoryRepositories;

import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.Author;
import com.uit.librarymanagementapplication.domain.entity.GenreCategory;
import com.uit.librarymanagementapplication.domain.repository.AuthorRepositories.AuthorRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GenreCategoryRepository implements IGenreCategoryRepository {

    private static GenreCategoryRepository instance;

    public GenreCategoryRepository() {

    }

    public static GenreCategoryRepository getInstance() {
        if (instance == null) {
            instance = new GenreCategoryRepository();
        }
        return instance;
    }

    @Override
    public List<GenreCategory> getAllCategories() {
        List<GenreCategory> genreCategories = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[GenreCategories] WHERE IsDelete = 0 ORDER BY GenreCategoryID desc";

        try (
            Connection conn = DbUtils.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GenreCategory genreCategory = new GenreCategory();
                genreCategory.setGenreCategoryID(rs.getInt("GenreCategoryID"));
                genreCategory.setNameCategory(rs.getString("NameCategory"));
                genreCategory.setGenreCategory(rs.getString("GenreCategory"));
                genreCategory.setIsDelete(rs.getInt("IsDelete"));
                genreCategory.setCreatedDt(rs.getTimestamp("CreatedDt"));
                genreCategory.setCreatedBy(rs.getString("CreatedBy"));
                genreCategory.setUpdateDt(rs.getTimestamp("UpdateDt"));
                genreCategory.setUpdateBy(rs.getString("UpdateBy"));
                genreCategories.add(genreCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close();
        }
        return genreCategories;
    }

}
