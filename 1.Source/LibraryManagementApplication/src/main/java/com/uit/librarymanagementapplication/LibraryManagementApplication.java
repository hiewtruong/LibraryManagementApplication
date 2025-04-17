/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.uit.librarymanagementapplication;

import com.uit.librarymanagementapplication.domain.DbUtils;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author hieutruong
 */
public class LibraryManagementApplication {

    public static void main(String[] args) throws SQLException {
        Connection conn = DbUtils.getConnection();
        System.out.println("Kết nối thành công!");
    }
}
