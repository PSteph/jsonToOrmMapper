package com.bopuniv.server.exceptions;

public class EntranceException extends RuntimeException {

    public EntranceException(){this("Entrance not found");}

    public EntranceException(String message){super(message);}
}
