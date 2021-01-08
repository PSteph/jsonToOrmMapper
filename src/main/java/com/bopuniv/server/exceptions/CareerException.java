package com.bopuniv.server.exceptions;

public class CareerException extends RuntimeException {

    public CareerException(){this("Career nor found");}
    public CareerException(String message){super(message);}
}
