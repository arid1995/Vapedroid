package com.dimwits.vaperoid.requests.entities;

import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;

/**
 * Created by farid on 5/22/17.
 */

public class RegistrationEntity {
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String avatarPath;
    private String about;

    public RegistrationEntity(String login, String password, String email, String firstName,
                              String lastName, String avatarPath, String about) {
        if (login.length() < AuthenticationEntity.LOGIN_LENGTH ||
                password.length() < AuthenticationEntity.PASSWORD_LENGTH ||
                email == null ||
                firstName == null ||
                lastName == null ||
                avatarPath == null) {
            throw new ViolatedConstraintsException("Some values are not appropriate");
        }
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarPath = avatarPath;
        this.about = about;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public String getAbout() {
        return about;
    }
}
