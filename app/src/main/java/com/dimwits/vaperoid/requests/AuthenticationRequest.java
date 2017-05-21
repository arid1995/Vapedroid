package com.dimwits.vaperoid.requests;

import com.dimwits.vaperoid.requests.entities.LoginEntity;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.network.NetworkConstants;
import com.dimwits.vaperoid.utils.network.NetworkHelper;
import com.google.gson.Gson;

/**
 * Created by farid on 5/21/17.
 */

public class AuthenticationRequest extends Request {
    int taskId = 0;

    public void authenticate(ResponseListener listener, String login, String password) {
        Gson gson = new Gson();
        LoginEntity loginEntity = new LoginEntity(login, password);
        String jsonRepresentation = gson.toJson(loginEntity);
        taskId = NetworkHelper.getInstance().send(listener,
                NetworkHelper.POST, jsonRepresentation,
                NetworkConstants.IP_ADDRESS + "/api/session");
    }
}
