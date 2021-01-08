package com.bopuniv.server.exceptions;

public class DepartmentException extends RuntimeException {

    public DepartmentException() {
        this("Department not found");
    }

    public DepartmentException(String message) {
            super(message);
        }
}
