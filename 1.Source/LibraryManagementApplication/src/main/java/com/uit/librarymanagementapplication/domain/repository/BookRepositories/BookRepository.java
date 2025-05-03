/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.BookRepositories;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailRequestDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public class BookRepository implements IBookRepository {

    private static BookRepository instance;

    public BookRepository() {

    }

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Books] WHERE IsDelete = 0 ORDER BY BookID DESC";

        try (
                Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();
                book.setBookID(rs.getInt("BookID"));
                book.setTitle(rs.getString("Title"));
                book.setAuthor(rs.getString("Author"));
                book.setCover(rs.getString("Cover"));
                book.setLandingPage(rs.getString("LandingPage"));
                book.setHashtag(rs.getString("Hashtag"));
                book.setGenreCategory(rs.getString("GenreCategory"));
                book.setPublisher(rs.getString("Publisher"));
                book.setPublishYear(rs.getDate("PublishYear"));
                book.setLocation(rs.getString("Location"));
                book.setIsDisplay(rs.getInt("IsDisplay"));
                book.setQtyOH(rs.getInt("QtyOH"));
                book.setQtyAllocated(rs.getInt("QtyAllocated"));
                book.setIsDelete(rs.getInt("IsDelete"));
                book.setCreatedDt(rs.getTimestamp("CreatedDt"));
                book.setCreatedBy(rs.getString("CreatedBy"));
                book.setUpdateDt(rs.getTimestamp("UpdateDt"));
                book.setUpdateBy(rs.getString("UpdateBy"));

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close();
        }

        return books;
    }

    @Override
    public void updateQtyAllocated(List<TransactionLoanDetailRequestDTO> loanDetails) {
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "UPDATE Books SET QtyAllocated = QtyAllocated + 1 WHERE BookID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (TransactionLoanDetailRequestDTO detail : loanDetails) {
                    stmt.setLong(1, detail.getLoadBookID());
                    stmt.addBatch();
                }
                int[] rowsAffected = stmt.executeBatch();
                for (int rows : rowsAffected) {
                    if (rows == 0) {
                        throw new SQLException("Updating QtyAllocated failed, no rows affected.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating QtyAllocated: " + e.getMessage(), e);
        }
    }

    @Override
    public void decrementQtyAllocated(List<TransactionLoanDetailRequestDTO> loanDetails) {
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "UPDATE Books SET QtyAllocated = QtyAllocated - 1 WHERE BookID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (TransactionLoanDetailRequestDTO detail : loanDetails) {
                    stmt.setLong(1, detail.getLoadBookID());
                    stmt.addBatch();
                }
                int[] rowsAffected = stmt.executeBatch();
                for (int rows : rowsAffected) {
                    if (rows == 0) {
                        throw new SQLException("Decrementing QtyAllocated failed, no rows affected.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error decrementing QtyAllocated: " + e.getMessage(), e);
        }
    }

}
