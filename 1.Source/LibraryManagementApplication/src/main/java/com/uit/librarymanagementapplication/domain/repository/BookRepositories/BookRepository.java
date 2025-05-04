/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uit.librarymanagementapplication.domain.repository.BookRepositories;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailRequestDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Book> findAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Books] WHERE IsDelete = 0";

        try (Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Cover"),
                        rs.getString("LandingPage"),
                        rs.getString("Hashtag"),
                        rs.getString("GenreCategory"),
                        rs.getString("Publisher"),
                        rs.getDate("PublishYear"),
                        rs.getString("Location"),
                        rs.getInt("IsDisplay"),
                        rs.getInt("QtyOH"),
                        rs.getInt("QtyAllocated"),
                        rs.getInt("IsDelete"),
                        rs.getDate("CreatedDt"),
                        rs.getString("CreatedBy"),
                        rs.getDate("UpdateDt"),
                        rs.getString("UpdateBy")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Book findBookById(int bookID) {
        Book book = null;
        String sql = "SELECT * FROM [dbo].[Books] WHERE BookID = ? AND IsDelete = 0";

        try (Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book(
                            rs.getInt("BookID"),
                            rs.getString("Title"),
                            rs.getString("Author"),
                            rs.getString("Cover"),
                            rs.getString("LandingPage"),
                            rs.getString("Hashtag"),
                            rs.getString("GenreCategory"),
                            rs.getString("Publisher"),
                            rs.getDate("PublishYear"),
                            rs.getString("Location"),
                            rs.getInt("IsDisplay"),
                            rs.getInt("QtyOH"),
                            rs.getInt("QtyAllocated"),
                            rs.getInt("IsDelete"),
                            rs.getDate("CreatedDt"),
                            rs.getString("CreatedBy"),
                            rs.getDate("UpdateDt"),
                            rs.getString("UpdateBy")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public boolean createBook(Book book) {
        String sql = "INSERT INTO [dbo].[Books] "
                + "(Title, Author, Cover, LandingPage, Hashtag, GenreCategory, Publisher, PublishYear, Location, IsDisplay, QtyOH, QtyAllocated, IsDelete, CreatedDt, CreatedBy, UpdateDt, UpdateBy) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, GETDATE(), ?, GETDATE(), ?)";

        try (Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getCover());
            stmt.setString(4, book.getLandingPage());
            stmt.setString(5, book.getHashtag());
            stmt.setString(6, book.getGenreCategory());
            stmt.setString(7, book.getPublisher());
            stmt.setDate(8, new java.sql.Date(book.getPublishYear().getTime()));
            stmt.setString(9, book.getLocation());
            stmt.setInt(10, book.getIsDisplay());
            stmt.setInt(11, book.getQtyOH());
            stmt.setInt(12, book.getQtyAllocated());
            stmt.setString(13, "admin"); // CreatedBy
            stmt.setString(14, "admin"); // UpdateBy

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateBook(Book book) {
        String sql = "UPDATE [dbo].[Books] SET "
                + "Title = ?, Author = ?, Cover = ?, LandingPage = ?, Hashtag = ?, GenreCategory = ?, Publisher = ?, PublishYear = ?, Location = ?, IsDisplay = ?, QtyOH = ?, QtyAllocated = ?, UpdateDt = GETDATE(), UpdateBy = ? "
                + "WHERE BookID = ? AND IsDelete = 0";

        try (Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getCover());
            stmt.setString(4, book.getLandingPage());
            stmt.setString(5, book.getHashtag());
            stmt.setString(6, book.getGenreCategory());
            stmt.setString(7, book.getPublisher());
            stmt.setDate(8, new java.sql.Date(book.getPublishYear().getTime()));
            stmt.setString(9, book.getLocation());
            stmt.setInt(10, book.getIsDisplay());
            stmt.setInt(11, book.getQtyOH());
            stmt.setInt(12, book.getQtyAllocated());
            stmt.setString(13, "admin"); // UpdateBy
            stmt.setInt(14, book.getBookID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteBook(int bookID) {
        String sql = "UPDATE [dbo].[Books] SET IsDelete = 1 WHERE BookID = ?";

        try (Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Book> findByTitile(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Books] WHERE Title LIKE %?%";

        try (Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("BookID"),
                            rs.getString("Title"),
                            rs.getString("Author"),
                            rs.getString("Cover"),
                            rs.getString("LandingPage"),
                            rs.getString("Hashtag"),
                            rs.getString("GenreCategory"),
                            rs.getString("Publisher"),
                            rs.getDate("PublishYear"),
                            rs.getString("Location"),
                            rs.getInt("IsDisplay"),
                            rs.getInt("QtyOH"),
                            rs.getInt("QtyAllocated"),
                            rs.getInt("IsDelete"),
                            rs.getDate("CreatedDt"),
                            rs.getString("CreatedBy"),
                            rs.getDate("UpdateDt"),
                            rs.getString("UpdateBy")
                    );
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public void updateBookQuantity(int bookId, int qtyOH, int qtyAllocated) {
        String sql = "UPDATE Books SET QtyOH = ?, QtyAllocated = ? WHERE BookID = ?";

        try (Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, qtyOH);
            stmt.setInt(2, qtyAllocated);
            stmt.setInt(3, bookId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
