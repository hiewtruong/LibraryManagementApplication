/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.DTO.User;

import java.util.Date;

/**
 *
 * @author hieutruong
 */
public class UserRoleDTO {
    private int userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private int gender;
    private String email;
    private String phone;
    private String address;
    private int userRoleID_FK;
    private int isDelete;
    private int isAdmin;
    private String roleName;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserRoleID_FK() {
        return userRoleID_FK;
    }

    public void setUserRoleID_FK(int userRoleID_FK) {
        this.userRoleID_FK = userRoleID_FK;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public UserRoleDTO(int userID, String firstName, String lastName, String userName, String password, int gender, String email, String phone, String address, int userRoleID_FK, int isDelete, int isAdmin, String roleName) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.userRoleID_FK = userRoleID_FK;
        this.isDelete = isDelete;
        this.isAdmin = isAdmin;
        this.roleName = roleName;
    }
}
