package com.bopuniv.server.entities;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.util.UUID;

@Entity
public class RegistrationToken {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String token;

    public String email;

    public RegistrationToken(){
        token = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
