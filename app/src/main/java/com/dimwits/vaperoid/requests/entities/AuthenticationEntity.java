package com.dimwits.vaperoid.requests.entities;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;

/**
 * Created by farid on 5/21/17.
 */

public class AuthenticationEntity {
    public static final int LOGIN_LENGTH = 5;
    public static final int PASSWORD_LENGTH = 8;

    private String login;
    private String password;

    public AuthenticationEntity(String login, String password) throws ViolatedConstraintsException {
        if (login.length() < LOGIN_LENGTH || password.length() < PASSWORD_LENGTH) {
            throw new ViolatedConstraintsException("Some values are not appropriate");
        }
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
