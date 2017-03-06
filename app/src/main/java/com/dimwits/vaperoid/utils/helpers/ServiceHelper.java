package com.dimwits.vaperoid.utils.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.dimwits.vaperoid.services.GetTextIntentService;

/**
 * Created by farid on 3/4/17.
 */

public class ServiceHelper {

    private static ServiceHelper serviceHelper;

    private ServiceHelper() {
    }

    public static synchronized ServiceHelper getInstance(Context context) {
        if (serviceHelper == null) {
            serviceHelper = new ServiceHelper();
            serviceHelper.initBroadcastReceiver(context);
        }

        return serviceHelper;
    }

    public void getText(Context context, String uri) {
        Intent intent = initIntent(context, uri);
        context.startService(intent);
    }

    private Intent initIntent(Context context, String url) {
        Intent intent = new Intent(context, GetTextIntentService.class);
        intent.setAction(GetTextIntentService.ACTION_LOAD_TEXT);
        intent.putExtra(GetTextIntentService.EXTRA_URL, url);
        return intent;
    }

    private void initBroadcastReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GetTextIntentService.ACTION_TEXT_READY);

        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Dayamn, " +
                        intent.getStringExtra(GetTextIntentService.EXTRA_RESPONSE),
                        Toast.LENGTH_SHORT).show();
            }
        }, filter);
    }
}
