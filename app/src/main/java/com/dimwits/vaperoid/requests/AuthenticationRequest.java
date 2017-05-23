package com.dimwits.vaperoid.requests;

import com.dimwits.vaperoid.requests.entities.AuthenticationEntity;
import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.network.NetworkConstants;
import com.dimwits.vaperoid.utils.network.NetworkHelper;
import com.google.gson.Gson;

public class AuthenticationRequest extends Request {
    public void authenticate(ResponseListener listener,
                             String login, String password) throws ViolatedConstraintsException {
        Gson gson = new Gson();
        AuthenticationEntity authenticationEntity = new AuthenticationEntity(login, password);
        String jsonRepresentation = gson.toJson(authenticationEntity);
        taskId = NetworkHelper.getInstance().send(listener,
                NetworkHelper.POST, jsonRepresentation,
                NetworkConstants.IP_ADDRESS + "/api/session");
    }
}
