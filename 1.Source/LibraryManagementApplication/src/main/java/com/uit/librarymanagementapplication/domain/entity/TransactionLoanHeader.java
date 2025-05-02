package com.uit.librarymanagementapplication.domain.entity;

import java.util.Date;

public class TransactionLoanHeader {

    private int loanHeaderID;
    private String loanTicketNumber;
    private int userID;
    private int totalQty;
    private Date loanDt;
    private Date loanReturnDt;
    private int isDelete;
    private String createdBy;
    private Date createdDt;
    private String updateBy;
    private Date updateDt;
    private int status;

    public TransactionLoanHeader() {
    }

    public TransactionLoanHeader(int loanHeaderID, String loanTicketNumber, int userID, int totalQty, Date loanDt, Date loanReturnDt, int isDelete, String createdBy, Date createdDt, String updateBy, Date updateDt, int status) {
        this.loanHeaderID = loanHeaderID;
        this.loanTicketNumber = loanTicketNumber;
        this.userID = userID;
        this.totalQty = totalQty;
        this.loanDt = loanDt;
        this.loanReturnDt = loanReturnDt;
        this.isDelete = isDelete;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.updateBy = updateBy;
        this.updateDt = updateDt;
        this.status = status;
    }

    public int getLoanHeaderID() {
        return loanHeaderID;
    }

    public void setLoanHeaderID(int loanHeaderID) {
        this.loanHeaderID = loanHeaderID;
    }

    public String getLoanTicketNumber() {
        return loanTicketNumber;
    }

    public void setLoanTicketNumber(String loanTicketNumber) {
        this.loanTicketNumber = loanTicketNumber;
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

    public Date getLoanDt() {
        return loanDt;
    }

    public void setLoanDt(Date loanDt) {
        this.loanDt = loanDt;
    }

    public Date getLoanReturnDt() {
        return loanReturnDt;
    }

    public void setLoanReturnDt(Date loanReturnDt) {
        this.loanReturnDt = loanReturnDt;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
