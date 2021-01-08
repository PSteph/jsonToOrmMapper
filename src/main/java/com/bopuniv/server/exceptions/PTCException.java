package com.bopuniv.server.exceptions;

public class PTCException extends RuntimeException {

    public PTCException() {
        this("PTC not found");
    }

    public PTCException(String message) {
        super(message);
    }
}
