package com.uit.librarymanagementapplication.view.lib;

import com.uit.librarymanagementapplication.domain.DTO.Message;
import com.uit.librarymanagementapplication.lib.ApiException;
import javax.swing.*;
import java.awt.*;

public class CommonUI {

    public static void showErrorApi(Component parent, ApiException exception) {
        Message error = exception.getError();
        String fullMessage = error.getCode() + " - " + error.getMessage();
        JOptionPane.showMessageDialog(
                parent,
                fullMessage,
                error.getTitle(),
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void showInfoApi(Component parent, ApiException exception) {
        Message error = exception.getError();
        String fullMessage = error.getCode() + " - " + error.getMessage();
        JOptionPane.showMessageDialog(
                parent,
                fullMessage,
                error.getTitle(),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void showWarningApi(Component parent, ApiException exception) {
        Message error = exception.getError();
        String fullMessage = error.getCode() + " - " + error.getMessage();
        JOptionPane.showMessageDialog(
                parent,
                fullMessage,
                error.getTitle(),
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void showAlerValidate(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Validate",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Successful",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static int showConfirmDialog(Component parent, String message, String title) {
        int response = JOptionPane.showConfirmDialog(
                parent,
                message,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return response;
    }

}
