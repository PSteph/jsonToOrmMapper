package com.bopuniv.server.exceptions;

public class IllegalUserAccess extends RuntimeException{
    public IllegalUserAccess(){this("You are not allowed to perform this operation");}
    public IllegalUserAccess(String message){
        super(message);
    }
}
