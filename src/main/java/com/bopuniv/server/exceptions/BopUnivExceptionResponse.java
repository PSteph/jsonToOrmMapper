package com.bopuniv.server.exceptions;

import java.time.LocalDateTime;

/**
 * Defining a common structure for exceptions returned to users
 */
public class BopUnivExceptionResponse {

    private LocalDateTime timestamp;
    private String  message;
    private String details;

    public BopUnivExceptionResponse(LocalDateTime timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}