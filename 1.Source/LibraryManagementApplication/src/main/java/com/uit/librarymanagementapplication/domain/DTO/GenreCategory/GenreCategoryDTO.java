/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.domain.DTO.GenreCategory;

/**
 *
 * @author hieutruong
 */
public class GenreCategoryDTO {
    private int genreCategoryID;
    private String nameCategory;

    public GenreCategoryDTO() {
    }

    public GenreCategoryDTO(int genreCategoryID, String nameCategory) {
        this.genreCategoryID = genreCategoryID;
        this.nameCategory = nameCategory;
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
    
    
}
