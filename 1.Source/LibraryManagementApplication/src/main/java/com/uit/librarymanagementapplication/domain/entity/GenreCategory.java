
package com.uit.librarymanagementapplication.domain.entity;

import java.util.Date;


public class GenreCategory {
    
    private int genreCategoryID;
    private String nameCategory;
    private String genreCategory;
    private int isDelete;
    private Date createdDt;
    private String createdBy;
    private Date updateDt;
    private String updateBy;

    public GenreCategory() {
    }

    
    
    public GenreCategory(int genreCategoryID, String nameCategory, String genreCategory, int isDelete, Date createdDt, String createdBy, Date updateDt, String updateBy) {
        this.genreCategoryID = genreCategoryID;
        this.nameCategory = nameCategory;
        this.genreCategory = genreCategory;
        this.isDelete = isDelete;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updateDt = updateDt;
        this.updateBy = updateBy;
    }

    public int getGenreCategoryID() {
        return genreCategoryID;
    }

    public void setGenreCategoryID(int genreCategoryID) {
        this.genreCategoryID = genreCategoryID;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getGenreCategory() {
        return genreCategory;
    }

    public void setGenreCategory(String genreCategory) {
        this.genreCategory = genreCategory;
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
