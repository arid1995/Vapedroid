package com.dimwits.vaperoid.utils.network;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by farid on 3/2/17.
 */

public class OkHttpConnector {
    private OkHttpClient okHttpClient = new OkHttpClient();

    private OkHttpConnector() {}

    public OkHttpClient getConnector() {
        return okHttpClient;
    }
}
