/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.entity;

import java.util.Date;

/**
 *
 * @author hieutruong
 */
public class Author {
    private int authorID;
    private String authorName;
    private int isDelete;
    private Date createdDt;
    private String createdBy;
    private Date updateDt;
    private String updateBy;

    public Author() {
    }
    
    public Author(int authorID, String authorName, int isDelete, Date createdDt, String createdBy, Date updateDt, String updateBy) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.isDelete = isDelete;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updateDt = updateDt;
        this.updateBy = updateBy;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
