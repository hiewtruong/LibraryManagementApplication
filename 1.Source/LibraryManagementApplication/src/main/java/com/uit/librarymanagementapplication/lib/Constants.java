package com.uit.librarymanagementapplication.lib;

public class Constants {

    public static final String ALGORITHM = "AES";
    public static final String ADMIN = "admin@uit.com";
    public static final int ADMIN_ROLE = 1;
    public static final int USER_ROLE = 2;

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

    public class DateFormat {

        public static final String MMddyyyy = "MM/dd/yyyy";
    }

    public class ConfirmConsts {

        public static final String CONFIRM_TITLE = "Confirm";
        public static final String CONFIRM_CONTENT = "Are you want to save?";
        public static final String CONFIRM_EXIT_CONTENT = "Are you want to exit?";
        public static final String CONFIRM_DELETE_CONTENT = "Are you want to delete?";
    }

    public class TransLoanStatusConsts {
        public static final int BORROW = 0;
        public static final int PAID = 1;
    }
}
