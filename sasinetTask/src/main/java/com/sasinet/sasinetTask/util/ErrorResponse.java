package com.sasinet.sasinetTask.util;

public class ErrorResponse {
    private String errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Getter and setter
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

