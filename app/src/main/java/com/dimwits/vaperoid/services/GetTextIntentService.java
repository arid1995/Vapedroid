package com.dimwits.vaperoid.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

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
        Intent intent = new Intent(ACTION_TEXT_READY);
        intent.putExtra(EXTRA_RESPONSE, "Dat works");

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
