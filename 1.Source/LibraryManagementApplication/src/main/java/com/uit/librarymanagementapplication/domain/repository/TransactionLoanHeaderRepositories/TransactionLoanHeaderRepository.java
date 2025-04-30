package com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoanHeader.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.Author;
import com.uit.librarymanagementapplication.domain.repository.AuthorRepositories.AuthorRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionLoanHeaderRepository implements ITransactionLoanHeaderRepository {

    private static TransactionLoanHeaderRepository instance;

    public TransactionLoanHeaderRepository() {

    }

    public static TransactionLoanHeaderRepository getInstance() {
        if (instance == null) {
            instance = new TransactionLoanHeaderRepository();
        }
        return instance;
    }

    @Override
    public List<TransactionLoanHeaderDTO> getAllTransHeader() {
        List<TransactionLoanHeaderDTO> transLoanHeader = new ArrayList<>();
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
                //authors.add(author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close();
        }
        return transLoanHeader;
    }
}

