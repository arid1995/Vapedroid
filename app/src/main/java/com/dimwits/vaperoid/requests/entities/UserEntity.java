package com.dimwits.vaperoid.requests.entities;

import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;

public class UserEntity {
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private String about;

    public UserEntity(String login, String password, String email, String firstName,
                      String lastName, String avatar, String about) {
        if (login.length() < AuthenticationEntity.LOGIN_LENGTH ||
                password.length() < AuthenticationEntity.PASSWORD_LENGTH ||
                email == null ||
                firstName == null ||
                lastName == null ||
                avatar == null) {
            throw new ViolatedConstraintsException("Some values are not appropriate");
        }
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.about = about;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAbout() {
        return about;
    }
}
