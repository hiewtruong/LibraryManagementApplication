package com.uit.librarymanagementapplication.domain.model;

import java.util.Date;


public class UserWishList {
    
    private int userWishListID;
    private Integer userID; 
    private Integer bookID;
    private int isDelete;
    private Date createdDt;
    private String createdBy;
    private Date updateDt;
    private String updateBy;

    public UserWishList(int userWishListID, Integer userID, Integer bookID, int isDelete, Date createdDt, String createdBy, Date updateDt, String updateBy) {
        this.userWishListID = userWishListID;
        this.userID = userID;
        this.bookID = bookID;
        this.isDelete = isDelete;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updateDt = updateDt;
        this.updateBy = updateBy;
    }

    public int getUserWishListID() {
        return userWishListID;
    }

    public void setUserWishListID(int userWishListID) {
        this.userWishListID = userWishListID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
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
