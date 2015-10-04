package com.gd.amik.dto;

public class Status {

    private final int code;
    private final String message;

    public Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
