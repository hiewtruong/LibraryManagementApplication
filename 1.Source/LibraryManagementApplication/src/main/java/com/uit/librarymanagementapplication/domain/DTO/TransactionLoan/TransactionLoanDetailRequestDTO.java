/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.DTO.TransactionLoan;

/**
 *
 * @author hieutruong
 */
public class TransactionLoanDetailRequestDTO {

    private int loadBookID;

    public TransactionLoanDetailRequestDTO() {
    }

    public TransactionLoanDetailRequestDTO(int loadBookID) {
        this.loadBookID = loadBookID;
    }

    public int getLoadBookID() {
        return loadBookID;
    }

    public void setLoadBookID(int loadBookID) {
        this.loadBookID = loadBookID;
    }

}
