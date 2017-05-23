package com.dimwits.vaperoid.requests.entities;

import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;


@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AuthenticationEntity {
    static final int LOGIN_LENGTH = 5;
    static final int PASSWORD_LENGTH = 8;

    private String login;
    private String password;

    public AuthenticationEntity(String login, String password) throws ViolatedConstraintsException {
        if (login.length() < LOGIN_LENGTH || password.length() < PASSWORD_LENGTH) {
            throw new ViolatedConstraintsException("Some values are not appropriate");
        }
        this.login = login;
        this.password = password;
    }
}
