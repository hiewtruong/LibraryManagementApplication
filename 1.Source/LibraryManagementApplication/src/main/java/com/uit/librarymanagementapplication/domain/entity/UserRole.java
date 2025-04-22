package com.uit.librarymanagementapplication.domain.entity;

import java.util.Date;

public class UserRole {
    
    private int userRoleID;
    private String roleName;
    private int isAdmin;
    private String type;
    private int isDelete;
    private Date createdDt;
    private String createdBy;
    private Date updateDt;
    private String updateBy;

    public UserRole(int userRoleID, String roleName, int isAdmin, String type, int isDelete, Date createdDt, String createdBy, Date updateDt, String updateBy) {
        this.userRoleID = userRoleID;
        this.roleName = roleName;
        this.isAdmin = isAdmin;
        this.type = type;
        this.isDelete = isDelete;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updateDt = updateDt;
        this.updateBy = updateBy;
    }

    public int getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(int userRoleID) {
        this.userRoleID = userRoleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
