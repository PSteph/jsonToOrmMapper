package com.bopuniv.server.exceptions;


public class TrainingException extends RuntimeException {

    public TrainingException(){this("Training not found");}
    public TrainingException(String message){super(message);}
}
