/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.DTO.TransactionLoanHeader;

import java.util.Date;

/**
 *
 * @author Hieu.Truong
 */
public class TransactionLoanHeaderDTO {
   private int loanHeaderID;
    private String loanTicketNumber;
    private int userID;
    private String useName;
    private String email;
    private String phone;
    private int totalQty;
    private Date loanDt;
    private Date loanReturnDt;
    private String createdBy;
    private Date createdDt;
    private String updateBy;
    private Date updateDt;

    public TransactionLoanHeaderDTO(int loanHeaderID, String loanTicketNumber, int userID, String useName, String email, String phone, int totalQty, Date loanDt, Date loanReturnDt, String createdBy, Date createdDt, String updateBy, Date updateDt) {
        this.loanHeaderID = loanHeaderID;
        this.loanTicketNumber = loanTicketNumber;
        this.userID = userID;
        this.useName = useName;
        this.email = email;
        this.phone = phone;
        this.totalQty = totalQty;
        this.loanDt = loanDt;
        this.loanReturnDt = loanReturnDt;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.updateBy = updateBy;
        this.updateDt = updateDt;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
