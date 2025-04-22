package com.uit.librarymanagementapplication.lib;


public class Constants {

    public static final String ALGORITHM = "AES";

    public class ErrorCode {

        public static final String USER_NOT_FOUND = "USR_01";
        public static final String PASSWORD_NOT_CORRECT = "USR_02";
        public static final String USER_HAS_BEEN_LOCKED = "USR_03";
        public static final String USER_IS_NOT_ADMIN = "USR_04";
    }

    public class ErrorMessage {
        public static final String USER_NOT_FOUND = "No matching user account found in the system.";
        public static final String PASSWORD_NOT_CORRECT = "Invalid username or password.";
        public static final String USER_HAS_BEEN_LOCKED = "Your account has been locked. Please contact the administrator.";
        public static final String USER_IS_NOT_ADMIN = "Your account login is not admin, Please verify it again.";
    }
    
     public class ErrorTitle {
         
        public static final String LOGIN = "LOGIN";
    }

    public class GeneralStatus {

        public static final int OPEN = 0;
        public static final int DELETE = 1;
    }
    
}
