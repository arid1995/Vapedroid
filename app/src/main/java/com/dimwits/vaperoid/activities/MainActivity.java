package com.dimwits.vaperoid.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.fragments.MenuFragment;
import com.dimwits.vaperoid.fragments.UnauthenticatedFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (isLoggedIn()) {
            transaction.replace(R.id.menu_login_container, new MenuFragment());
        } else {
            transaction.replace(R.id.menu_login_container, new UnauthenticatedFragment());
        }

        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                this.getString(R.string.shared_pref_file), Context.MODE_PRIVATE);
        String sessionId = sharedPreferences.getString(UnauthenticatedFragment.SESSION_ID_KEY, null);
        return sessionId != null;
    }
}
