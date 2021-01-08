package com.bopuniv.server.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
