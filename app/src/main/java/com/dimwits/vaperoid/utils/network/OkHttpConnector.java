package com.dimwits.vaperoid.utils.network;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by farid on 3/2/17.
 */

public class OkHttpConnector {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    private OkHttpConnector() {}

    public static synchronized OkHttpClient getConnector() {
        return okHttpClient;
    }
}
