package com.uit.librarymanagementapplication.domain.DTO.Book;

import java.util.Date;

public class BookDTO {

    private int bookID;
    private String title;
    private String author;
    private String cover;
    private String landingPage;
    private String hashtag;
    private String genreCategory;
    private String publisher;
    private Date publishYear;
    private String location;
    private int isDisplay;
    private int qtyOH;
    private int qtyAllocated;
    private int isDelete;
    private Date createdDt;
    private String createdBy;
    private Date updateDt;
    private String updateBy;

    public BookDTO(int bookID, String title, String author, String cover, String landingPage, String hashtag,
            String genreCategory, String publisher, Date publishYear, String location, int isDisplay,
            int qtyOH, int qtyAllocated, int isDelete, Date createdDt, String createdBy, Date updateDt, String updateBy) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.cover = cover;
        this.landingPage = landingPage;
        this.hashtag = hashtag;
        this.genreCategory = genreCategory;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.location = location;
        this.isDisplay = isDisplay;
        this.qtyOH = qtyOH;
        this.qtyAllocated = qtyAllocated;
        this.isDelete = isDelete;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updateDt = updateDt;
        this.updateBy = updateBy;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(int isDisplay) {
        this.isDisplay = isDisplay;
    }

    public int getQtyOH() {
        return qtyOH;
    }

    public void setQtyOH(int qtyOH) {
        this.qtyOH = qtyOH;
    }

    public int getQtyAllocated() {
        return qtyAllocated;
    }

    public void setQtyAllocated(int qtyAllocated) {
        this.qtyAllocated = qtyAllocated;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
