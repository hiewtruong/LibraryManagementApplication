package com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderRequestDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        List<TransactionLoanHeaderDTO> transLoanHeaderList = new ArrayList<>();

        String sql = """
        SELECT T.LoanHeaderID, T.LoanTicketNumber, T.UserID_FK, 
               U.UserName, U.Email, U.Phone, 
               T.TotalQty, T.LoanDt, T.LoanReturnDt, 
               T.CreatedBy, T.CreatedDt, T.UpdateBy, T.UpdateDt, T.Status
        FROM TransactionLoanHeaders AS T
        INNER JOIN Users AS U ON T.UserID_FK = U.UserID
        WHERE T.IsDelete = 0
        ORDER BY T.LoanHeaderID DESC
        """;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                TransactionLoanHeaderDTO dto = new TransactionLoanHeaderDTO();
                dto.setLoanHeaderID(rs.getInt("LoanHeaderID"));
                dto.setLoanTicketNumber(rs.getString("LoanTicketNumber"));
                dto.setUserID(rs.getInt("UserID_FK"));
                dto.setUseName(rs.getString("UserName"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhone(rs.getString("Phone"));
                dto.setTotalQty(rs.getInt("TotalQty"));
                dto.setLoanDt(rs.getDate("LoanDt"));
                dto.setLoanReturnDt(rs.getDate("LoanReturnDt"));
                dto.setCreatedBy(rs.getString("CreatedBy"));
                dto.setCreatedDt(rs.getTimestamp("CreatedDt"));
                dto.setUpdateBy(rs.getString("UpdateBy"));
                dto.setUpdateDt(rs.getTimestamp("UpdateDt"));
                dto.setStatus(rs.getInt("Status"));
                transLoanHeaderList.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transaction loan headers: " + e.getMessage(), e);
        } finally {
            DbUtils.close();
        }

        return transLoanHeaderList;
    }

    @Override
    public List<TransactionLoanHeaderDTO> getAllTransHeaderByKeyWord(String keyword, String column) {
        List<TransactionLoanHeaderDTO> transLoanHeaderList = new ArrayList<>();

        String sql = """
        SELECT T.LoanHeaderID, T.LoanTicketNumber, T.UserID_FK, 
               U.UserName, U.Email, U.Phone, 
               T.TotalQty, T.LoanDt, T.LoanReturnDt, 
               T.CreatedBy, T.CreatedDt, T.UpdateBy, T.UpdateDt, T.Status
        FROM TransactionLoanHeaders AS T
        INNER JOIN Users AS U ON T.UserID_FK = U.UserID
        WHERE T.IsDelete = 0
        AND %s LIKE ?
        ORDER BY T.LoanHeaderID DESC
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
                TransactionLoanHeaderDTO dto = new TransactionLoanHeaderDTO();
                dto.setLoanHeaderID(rs.getInt("LoanHeaderID"));
                dto.setLoanTicketNumber(rs.getString("LoanTicketNumber"));
                dto.setUserID(rs.getInt("UserID_FK"));
                dto.setUseName(rs.getString("UserName"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhone(rs.getString("Phone"));
                dto.setTotalQty(rs.getInt("TotalQty"));
                dto.setLoanDt(rs.getDate("LoanDt"));
                dto.setLoanReturnDt(rs.getDate("LoanReturnDt"));
                dto.setCreatedBy(rs.getString("CreatedBy"));
                dto.setCreatedDt(rs.getTimestamp("CreatedDt"));
                dto.setUpdateBy(rs.getString("UpdateBy"));
                dto.setUpdateDt(rs.getTimestamp("UpdateDt"));
                dto.setStatus(rs.getInt("Status"));
                transLoanHeaderList.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transaction loan headers: " + e.getMessage(), e);
        } finally {
            DbUtils.close();
        }

        return transLoanHeaderList;
    }

    @Override
    public int createTransactionLoanHeader(TransactionLoanHeaderRequestDTO requestDTO) {
//        Connection conn = DbUtils.getConnection;
//        
//        String sql = "INSERT INTO TransactionLoanHeader (userID, loanReturnDt, totalQty, loanDt, status, createdDt, createdBy) "
//                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
//            stmt.setLong(1, requestDTO.getUserID());
//            stmt.setDate(2, new java.sql.Date(requestDTO.getLoanReturnDt().getTime()));
//            stmt.setInt(3, requestDTO.getTotalQty());
//            stmt.setDate(4, new java.sql.Date(new Date().getTime())); // loanDt là ngày hiện tại
//            stmt.setString(5, "BORROW"); // Giả định trạng thái ban đầu là "BORROW"
//            stmt.setDate(6, new java.sql.Date(new Date().getTime())); // createdDt là ngày hiện tại
//            stmt.setString(7, "SYSTEM"); // createdBy tạm để là "SYSTEM"
//
//            int rowsAffected = stmt.executeUpdate();
//            if (rowsAffected == 0) {
//                throw new SQLException("Creating TransactionLoanHeader failed, no rows affected.");
//            }
//
//            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    return generatedKeys.getInt(1);
//                } else {
//                    throw new SQLException("Creating TransactionLoanHeader failed, no ID obtained.");
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving transaction loan headers: " + e.getMessage(), e);
//        }
        return 0;
    }

}
