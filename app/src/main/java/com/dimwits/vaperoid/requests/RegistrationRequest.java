package com.dimwits.vaperoid.requests;

import com.dimwits.vaperoid.requests.entities.UserEntity;
import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.network.NetworkConstants;
import com.dimwits.vaperoid.utils.network.NetworkHelper;
import com.google.gson.Gson;

/**
 * Created by farid on 5/21/17.
 */

public class RegistrationRequest extends Request {
    int taskId = 0;

    public void register(ResponseListener listener, String login, String password, String email,
                         String firstName, String lastName, String avatarPath,
                         String about) throws ViolatedConstraintsException {
        Gson gson = new Gson();
        UserEntity userEntity = new UserEntity(login, password, email,
                firstName, lastName, avatarPath, about);
        String jsonRepresentation = gson.toJson(userEntity);
        taskId = NetworkHelper.getInstance().send(listener,
                NetworkHelper.POST, jsonRepresentation,
                NetworkConstants.IP_ADDRESS + "/api/user");
    }
}
