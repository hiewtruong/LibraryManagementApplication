package com.uit.librarymanagementapplication.domain.entity;

import java.util.Date;


public class TransactionLoanDetail {
    
    private int loanDetailID;
    private int loadBookID;
    private int isDelete;
    private String createdBy;
    private Date createdDt;
    private String updateBy;
    private Date updateDt;

    public TransactionLoanDetail(int loanDetailID, int loadBookID, int isDelete, String createdBy, Date createdDt, String updateBy, Date updateDt) {
        this.loanDetailID = loanDetailID;
        this.loadBookID = loadBookID;
        this.isDelete = isDelete;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.updateBy = updateBy;
        this.updateDt = updateDt;
    }

    public int getLoanDetailID() {
        return loanDetailID;
    }

    public void setLoanDetailID(int loanDetailID) {
        this.loanDetailID = loanDetailID;
    }

    public int getLoadBookID() {
        return loadBookID;
    }

    public void setLoadBookID(int loadBookID) {
        this.loadBookID = loadBookID;
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
    
    
}
