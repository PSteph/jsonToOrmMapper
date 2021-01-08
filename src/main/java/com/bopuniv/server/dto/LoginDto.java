package com.bopuniv.server.dto;

import java.util.Objects;

public class LoginDto {

    private String password;
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginDto loginDto = (LoginDto) o;
        return Objects.equals(password, loginDto.password) &&
                Objects.equals(username, loginDto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, username);
    }

    @Override
    public String toString() {
        return "loginDto{" +
                "password='" + password + '\'' +
                ", email='" + username + '\'' +
                '}';
    }
}
