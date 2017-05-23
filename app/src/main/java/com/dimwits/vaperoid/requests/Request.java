package com.dimwits.vaperoid.requests;

import com.dimwits.vaperoid.utils.network.NetworkHelper;

public abstract class Request {
    int taskId = 0;

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
