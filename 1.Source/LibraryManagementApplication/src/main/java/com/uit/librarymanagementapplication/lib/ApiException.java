package com.uit.librarymanagementapplication.lib;

import com.uit.librarymanagementapplication.domain.model.Message;


public class ApiException extends RuntimeException {
    
    private final Message error;

    public ApiException(String title, String code, String message) {
        super(message);
        this.error = new Message(title, code, message);
    }

    public ApiException(Message message) {
        super(message.getMessage());
        this.error = message;
    }

    public ApiException(Message message, Throwable cause) {
        super(message.getMessage(), cause);
        this.error = message;
    }

    public Message getError() {
        return error;
    }
}
