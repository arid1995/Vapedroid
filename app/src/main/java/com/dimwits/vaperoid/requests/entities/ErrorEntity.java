package com.dimwits.vaperoid.requests.entities;

import com.google.gson.Gson;

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
