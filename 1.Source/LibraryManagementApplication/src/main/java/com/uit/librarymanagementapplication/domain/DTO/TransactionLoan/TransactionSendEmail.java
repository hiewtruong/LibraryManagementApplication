package com.uit.librarymanagementapplication.domain.DTO.TransactionLoan;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookSendEmail;
import java.util.Date;
import java.util.List;

public class TransactionSendEmail {

    private String loanTicketNumber;
    private String useName;
    private String email;
    private String phone;
    private int totalQty;
    private Date loanDt;
    private Date loanReturnDt;
    private List<BookSendEmail> bookDetails;

    public TransactionSendEmail() {
    }

    public TransactionSendEmail(String loanTicketNumber, String useName, String email, String phone, int totalQty, Date loanDt, Date loanReturnDt, List<BookSendEmail> bookDetails) {
        this.loanTicketNumber = loanTicketNumber;
        this.useName = useName;
        this.email = email;
        this.phone = phone;
        this.totalQty = totalQty;
        this.loanDt = loanDt;
        this.loanReturnDt = loanReturnDt;
        this.bookDetails = bookDetails;
    }

    public String getLoanTicketNumber() {
        return loanTicketNumber;
    }

    public void setLoanTicketNumber(String loanTicketNumber) {
        this.loanTicketNumber = loanTicketNumber;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
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

    public List<BookSendEmail> getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(List<BookSendEmail> bookDetails) {
        this.bookDetails = bookDetails;
    }

    
}
