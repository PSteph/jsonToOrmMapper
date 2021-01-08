package com.bopuniv.server.exceptions;

public class EnvironmentVariableException extends RuntimeException {

    public EnvironmentVariableException(){
        this("Cannot find env variables");
    }

    public EnvironmentVariableException(String message){
        super(message);
    }
}
