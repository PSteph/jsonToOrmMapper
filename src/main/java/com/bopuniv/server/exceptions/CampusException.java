package com.bopuniv.server.exceptions;

public class CampusException extends RuntimeException {

    public CampusException(){this("Campus not found");}
    public CampusException(String message){super(message);}
}
