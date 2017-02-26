package com.dimwits.vaperoid.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.fragments.MenuFragment;
import com.dimwits.vaperoid.fragments.UnathorizedFragment;

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
            transaction.replace(R.id.menu_login_container, new UnathorizedFragment());
        }

        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //TODO: implement method that checks if user is logged in
    private boolean isLoggedIn() {
        return false;
    }
}
