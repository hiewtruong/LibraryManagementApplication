package com.uit.librarymanagementapplication.domain.DTO.Author;

import java.util.Date;


public class AuthorDTO {
    private String authorName;
    private Date createdDt;
    private String createdBy;
    private Date updateDt;
    private String updateBy;

    public AuthorDTO() {
    }

    
    public AuthorDTO(String authorName, Date createdDt, String createdBy, Date updateDt, String updateBy) {
        this.authorName = authorName;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updateDt = updateDt;
        this.updateBy = updateBy;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
