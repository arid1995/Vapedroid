package com.dimwits.vaperoid.requests;

import com.dimwits.vaperoid.utils.network.NetworkHelper;

/**
 * Created by farid on 5/21/17.
 */

public abstract class Request {
    protected int taskId = 0;

    public void stopProcess() {
        NetworkHelper.getInstance().unsubscribe(taskId);
        taskId = 0;
    }

    public boolean isFinished() {
        return taskId == 0;
    }

    public void requestFinished() {
        taskId = 0;
    }
}
