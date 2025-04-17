package com.uit.librarymanagementapplication.domain.model;


public class Message {

    private String title;
    private String code;
    private String message;

    public Message(String title, String code, String message) {
        this.title = title;
        this.code = code;
        this.message = message;
    }

    public Message() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    

}
