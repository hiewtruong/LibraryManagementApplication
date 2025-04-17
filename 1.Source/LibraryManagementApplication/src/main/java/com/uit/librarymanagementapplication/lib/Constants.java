/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.lib;

/**
 *
 * @author Hieu.Truong
 */
public class Constants {

    public class ErrorCode {
        public static final String USER_NOT_FOUND = "USR_404";
        public static final String USER_ALREADY_EXISTS = "USR_409";

        public static final String BOOK_NOT_FOUND = "BOOK_404";
        public static final String BOOK_ALREADY_EXISTS = "BOOK_409";

        public static final String INTERNAL_ERROR = "SYS_500";
        public static final String BAD_REQUEST = "REQ_400";
        public static final String UNAUTHORIZED = "AUTH_401";
        public static final String FORBIDDEN = "AUTH_403";
    }
}
