/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.DTO.TransactionLoan;

import java.util.List;

/**
 *
 * @author hieutruong
 */
public class  TransactionLoanHeaderRevokeDTO{
    private int loanHeaderID;
    private List<TransactionLoanDetailRequestDTO> loanDetails;

    public TransactionLoanHeaderRevokeDTO() {
    }

    public TransactionLoanHeaderRevokeDTO(int loanHeaderID, List<TransactionLoanDetailRequestDTO> loanDetails) {
        this.loanHeaderID = loanHeaderID;
        this.loanDetails = loanDetails;
    }

    public int getLoanHeaderID() {
        return loanHeaderID;
    }

    public void setLoanHeaderID(int loanHeaderID) {
        this.loanHeaderID = loanHeaderID;
    }

    public List<TransactionLoanDetailRequestDTO> getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(List<TransactionLoanDetailRequestDTO> loanDetails) {
        this.loanDetails = loanDetails;
    }
    
    
}
