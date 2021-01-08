package com.bopuniv.server.website.util;

import com.bopuniv.server.entities.RegistrationToken;

public class RegistrationConfirm {
    private final String message = "success";
    private String token;

    public RegistrationConfirm(String token){
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
