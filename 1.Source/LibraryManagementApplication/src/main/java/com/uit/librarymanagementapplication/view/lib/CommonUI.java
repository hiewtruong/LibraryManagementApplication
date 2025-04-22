package com.uit.librarymanagementapplication.view.lib;

import com.uit.librarymanagementapplication.domain.DTO.Message;
import com.uit.librarymanagementapplication.lib.ApiException;
import javax.swing.*;
import java.awt.*;

public class CommonUI {

    public static void showError(Component parent, ApiException exception) {
        Message error = exception.getError();
        String fullMessage = error.getCode() + " - " + error.getMessage();
        JOptionPane.showMessageDialog(
                parent,
                fullMessage,
                error.getTitle(),
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void showInfo(Component parent, ApiException exception) {
        Message error = exception.getError();
        String fullMessage = error.getCode() + " - " + error.getMessage();
        JOptionPane.showMessageDialog(
                parent,
                fullMessage,
                error.getTitle(),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void showWarning(Component parent, ApiException exception) {
        Message error = exception.getError();
        String fullMessage = error.getCode() + " - " + error.getMessage();
        JOptionPane.showMessageDialog(
                parent,
                fullMessage,
                error.getTitle(),
                JOptionPane.WARNING_MESSAGE
        );
    }
}
