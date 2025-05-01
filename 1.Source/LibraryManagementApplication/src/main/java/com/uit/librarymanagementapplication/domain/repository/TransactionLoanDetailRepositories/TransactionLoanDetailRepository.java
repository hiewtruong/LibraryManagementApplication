/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.repository.TransactionLoanDetailRepositories;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public class TransactionLoanDetailRepository implements ITransactionLoanDetailRepository {

    private static TransactionLoanDetailRepository instance;

    public TransactionLoanDetailRepository() {

    }

    public static TransactionLoanDetailRepository getInstance() {
        if (instance == null) {
            instance = new TransactionLoanDetailRepository();
        }
        return instance;
    }

    @Override
    public List<TransactionLoanDetailDTO> gettTransactionLoanByHeaderID(int loanHeaderID) {
        List<TransactionLoanDetailDTO> transLoanDetailList = new ArrayList<>();

        String sql = """
        SELECT T.LoanDetailID, T.LoanHeaderID, T.LoadBookID, T.CreatedBy, T.CreatedDt, T.UpdateBy, T.UpdateDt, 
               B.BookID, B.Title, B.Author, B.GenreCategory, B.Publisher, B.PublishYear 
        FROM TransactionLoanDetails AS T
        INNER JOIN Books AS B ON T.LoadBookID = B.BookID
        WHERE T.IsDelete = 0 AND T.LoanHeaderID = ?
        """;

        try (
                Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loanHeaderID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TransactionLoanDetailDTO dto = new TransactionLoanDetailDTO();
                    dto.setLoanDetailID(rs.getInt("LoanDetailID"));
                    dto.setLoanHeaderID(rs.getInt("LoanHeaderID"));
                    dto.setLoadBookID(rs.getInt("LoadBookID"));
                    dto.setCreatedBy(rs.getString("CreatedBy"));
                    dto.setCreatedDt(rs.getDate("CreatedDt"));
                    dto.setUpdateBy(rs.getString("UpdateBy"));
                    dto.setUpdateDt(rs.getDate("UpdateDt"));
                    dto.setBookID(rs.getInt("BookID"));
                    dto.setTitle(rs.getString("Title"));
                    dto.setAuthor(rs.getString("Author"));
                    dto.setGenreCategory(rs.getString("GenreCategory"));
                    dto.setPublisher(rs.getString("Publisher"));
                    dto.setPublishYear(rs.getDate("PublishYear"));

                    transLoanDetailList.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close();
        }

        return transLoanDetailList;
    }

}
