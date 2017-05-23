package com.dimwits.vaperoid.requests.exceptions;

public class ViolatedConstraintsException extends RuntimeException {

    public ViolatedConstraintsException(String message) {
        super(message);
    }

    public ViolatedConstraintsException(String message, Throwable cause) {
        super(message, cause);
    }
}
