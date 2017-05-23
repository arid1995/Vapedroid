package com.dimwits.vaperoid.requests;

import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.network.NetworkConstants;
import com.dimwits.vaperoid.utils.network.NetworkHelper;

public class UserRequest extends Request {
    public void getUserBySessionId(ResponseListener listener, String sessionId) {
        taskId = NetworkHelper.getInstance().send(listener,
                NetworkHelper.GET, null,
                NetworkConstants.IP_ADDRESS + "/api/user/session/" + sessionId);
    }
}
