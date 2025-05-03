package com.uit.librarymanagementapplication.domain.DTO.User;

import java.util.Date;

public class UserWishListDTO {

    private Integer userID_FK;
    private Integer bookID_FK;
    private Integer isDelete;
    private Date createdDt;
    private String createdBy;
    private Date updateDt;
    private String updateBy;

    public UserWishListDTO(Integer userID_FK, Integer bookID_FK, Integer isDelete, Date createdDt, String createdBy, Date updateDt, String updateBy) {
        this.userID_FK = userID_FK;
        this.bookID_FK = bookID_FK;
        this.isDelete = isDelete;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updateDt = updateDt;
        this.updateBy = updateBy;
    }

    

    public Integer getUserID_FK() {
        return userID_FK;
    }

    public void setUserID_FK(Integer userID_FK) {
        this.userID_FK = userID_FK;
    }

    public Integer getBookID_FK() {
        return bookID_FK;
    }

    public void setBookID_FK(Integer bookID_FK) {
        this.bookID_FK = bookID_FK;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
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
