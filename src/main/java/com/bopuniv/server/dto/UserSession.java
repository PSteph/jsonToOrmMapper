package com.bopuniv.server.dto;

import com.bopuniv.server.entities.User;

public class UserSession {
    private String name;

    public UserSession(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
