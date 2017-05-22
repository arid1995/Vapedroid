package com.dimwits.vaperoid.requests.entities;

import com.google.gson.Gson;

/**
 * Created by farid on 5/22/17.
 */

public class ErrorEntity {
    private String message;

    public ErrorEntity(String message) {
        Gson gson = new Gson();
        this.message = gson.fromJson(message, ErrorEntity.class).message;
    }

    public String getMessage() {
        return message;
    }
}
