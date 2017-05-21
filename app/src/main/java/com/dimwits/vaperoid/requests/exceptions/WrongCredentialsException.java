package com.dimwits.vaperoid.requests.exceptions;

/**
 * Created by farid on 5/21/17.
 */

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String message) {
        super(message);
    }
}
