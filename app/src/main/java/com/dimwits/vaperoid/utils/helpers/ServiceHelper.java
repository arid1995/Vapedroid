package com.dimwits.vaperoid.utils.helpers;

import android.content.Context;
import android.content.Intent;

import com.dimwits.vaperoid.services.GetTextIntentService;

/**
 * Created by farid on 3/4/17.
 */

public class ServiceHelper {
    public void getText(Context context, String uri) {
        Intent intent = initIntent(context, uri);
    }

    private Intent initIntent(Context context, String uri) {
        Intent intent = new Intent();
        intent.putExtra(GetTextIntentService.EXTRA_URL, uri);
        return intent;
    }
}
