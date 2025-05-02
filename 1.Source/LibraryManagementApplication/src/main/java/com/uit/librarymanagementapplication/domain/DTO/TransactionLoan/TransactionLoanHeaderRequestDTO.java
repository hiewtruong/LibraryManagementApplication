package com.uit.librarymanagementapplication.domain.DTO.TransactionLoan;

import java.util.Date;
import java.util.List;


public class TransactionLoanHeaderRequestDTO {

    private int userID;
    private int totalQty;
    private Date loanReturnDt;
    private List<TransactionLoanDetailRequestDTO> loanDetails;

    public TransactionLoanHeaderRequestDTO() {
    }

    public TransactionLoanHeaderRequestDTO(int userID, int totalQty, Date loanReturnDt, List<TransactionLoanDetailRequestDTO> loanDetails) {
        this.userID = userID;
        this.totalQty = totalQty;
        this.loanReturnDt = loanReturnDt;
        this.loanDetails = loanDetails;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public Date getLoanReturnDt() {
        return loanReturnDt;
    }

    public void setLoanReturnDt(Date loanReturnDt) {
        this.loanReturnDt = loanReturnDt;
    }

    public List<TransactionLoanDetailRequestDTO> getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(List<TransactionLoanDetailRequestDTO> loanDetails) {
        this.loanDetails = loanDetails;
    }
}
