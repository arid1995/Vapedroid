package com.dimwits.vaperoid.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by farid on 3/4/17.
 */

public class GetTextIntentService extends IntentService {

    public final static String EXTRA_URL = "extra.URL";

    GetTextIntentService() {
        super("TextGetter");
    }

    @Override
    public void onHandleIntent(Intent intent) {
    }
}
