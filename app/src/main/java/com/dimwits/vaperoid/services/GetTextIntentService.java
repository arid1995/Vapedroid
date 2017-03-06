package com.dimwits.vaperoid.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.dimwits.vaperoid.utils.network.OkHttpConnector;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by farid on 3/4/17.
 */

public class GetTextIntentService extends IntentService {

    public final static String EXTRA_URL = "extra.URL";
    public final static String ACTION_LOAD_TEXT = "action.LOAD_TEXT";

    public final static String ACTION_TEXT_READY = "action.TEXT_READY";
    public final static String EXTRA_RESPONSE = "extra.RESPONSE";

    public GetTextIntentService() {
        super("TextGetter");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        requestUrl(intent.getStringExtra(EXTRA_URL));
    }

    private void requestUrl(String url) {
        OkHttpClient connector = OkHttpConnector.getConnector();

        try {
            Request request = new Request.Builder().url(url).build();

            Response response = connector.newCall(request).execute();

            Intent intent = new Intent(ACTION_TEXT_READY);
            intent.putExtra(EXTRA_RESPONSE, response.body().string());

            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (IOException ex) {
            Intent intent = new Intent(ACTION_TEXT_READY);
            intent.putExtra(EXTRA_RESPONSE, "Connection problem");

            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
}
