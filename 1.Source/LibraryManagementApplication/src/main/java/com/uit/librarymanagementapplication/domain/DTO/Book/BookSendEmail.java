package com.uit.librarymanagementapplication.domain.DTO.Book;

public class BookSendEmail {

    private int bookID;
    private String title;
    private String author;

    public BookSendEmail() {
    }

    public BookSendEmail(int bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
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

    
}
