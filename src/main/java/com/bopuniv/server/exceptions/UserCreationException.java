package com.bopuniv.server.exceptions;


public class UserCreationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 2252098318305984250L;

    public UserCreationException() {
        super();
    }

    public UserCreationException(String message) {
        super(message);
    }
}