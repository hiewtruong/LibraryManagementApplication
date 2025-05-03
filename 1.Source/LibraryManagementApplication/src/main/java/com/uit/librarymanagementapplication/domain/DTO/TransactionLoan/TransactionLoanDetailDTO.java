/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.DTO.TransactionLoan;

import java.util.Date;

/**
 *
 * @author hieutruong
 */
public class TransactionLoanDetailDTO {
    
    private int loanDetailID;
    private int loanHeaderID;
    private int loanBookID;
    private String createdBy;
    private Date createdDt;
    private String updateBy;
    private Date updateDt;
    private int bookID;
    private String title;
    private String author;
    private String genreCategory;
    private String publisher;
    private Date publishYear;

    public TransactionLoanDetailDTO() {
    }

    
    public TransactionLoanDetailDTO(int loanDetailID, int loanHeaderID, int loanBookID, String createdBy, Date createdDt, String updateBy, Date updateDt, int bookID, String title, String author, String genreCategory, String publisher, Date publishYear) {
        this.loanDetailID = loanDetailID;
        this.loanHeaderID = loanHeaderID;
        this.loanBookID = loanBookID;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.updateBy = updateBy;
        this.updateDt = updateDt;
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genreCategory = genreCategory;
        this.publisher = publisher;
        this.publishYear = publishYear;
    }

    public int getLoanDetailID() {
        return loanDetailID;
    }

    public void setLoanDetailID(int loanDetailID) {
        this.loanDetailID = loanDetailID;
    }

    public int getLoanHeaderID() {
        return loanHeaderID;
    }

    public void setLoanHeaderID(int loanHeaderID) {
        this.loanHeaderID = loanHeaderID;
    }

    public int getLoanBookID() {
        return loanBookID;
    }

    public void setLoanBookID(int loanBookID) {
        this.loanBookID = loanBookID;
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

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenreCategory() {
        return genreCategory;
    }

    public void setGenreCategory(String genreCategory) {
        this.genreCategory = genreCategory;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Date publishYear) {
        this.publishYear = publishYear;
    }
    
    
}
