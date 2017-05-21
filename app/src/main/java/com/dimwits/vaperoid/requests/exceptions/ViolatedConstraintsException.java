package com.dimwits.vaperoid.requests.exceptions;

/**
 * Created by farid on 5/21/17.
 */

public class ViolatedConstraintsException extends RuntimeException {

    public ViolatedConstraintsException(String message) {
        super(message);
    }

    public ViolatedConstraintsException(String message, Throwable cause) {
        super(message, cause);
    }
}
