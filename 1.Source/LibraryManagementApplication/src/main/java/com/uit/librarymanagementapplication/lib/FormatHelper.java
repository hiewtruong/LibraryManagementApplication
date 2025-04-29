/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.lib;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hieutruong
 */
public class FormatHelper {
    
     public static String formatDate(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
