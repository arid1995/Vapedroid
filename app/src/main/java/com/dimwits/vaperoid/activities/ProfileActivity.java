package com.dimwits.vaperoid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.fragments.UnauthenticatedFragment;
import com.dimwits.vaperoid.requests.UserRequest;
import com.dimwits.vaperoid.requests.entities.UserEntity;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.network.NetworkConstants;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by farid on 5/23/17.
 */

public class ProfileActivity extends AppCompatActivity implements ResponseListener {
    private ImageView userPictureView;
    private TextView usernameField;
    private TextView emailField;
    private TextView firstNameField;
    private TextView lastNameField;
    private TextView aboutField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userPictureView = (ImageView) findViewById(R.id.profile_picture);
        usernameField = (TextView) findViewById(R.id.profile_username);
        emailField = (TextView) findViewById(R.id.profile_email);
        firstNameField = (TextView) findViewById(R.id.profile_first_name);
        lastNameField = (TextView) findViewById(R.id.profile_last_name);
        aboutField = (TextView) findViewById(R.id.profile_about);

        SharedPreferences sharedPreferences = this.getSharedPreferences(
                this.getString(R.string.shared_pref_file), Context.MODE_PRIVATE);
        String sessionId = sharedPreferences.getString(UnauthenticatedFragment.SESSION_ID_KEY, null);
        UserRequest userRequest = new UserRequest();
        userRequest.getUserBySessionId(this, sessionId);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResponseFinished(String response, boolean isSuccessful) {
        Gson gson = new Gson();
        UserEntity user = gson.fromJson(response, UserEntity.class);
        usernameField.setText(user.getLogin());
        emailField.setText(user.getEmail());
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        aboutField.setText(user.getAbout());
        Picasso.with(this).load(NetworkConstants.IP_ADDRESS + "/" + user.getAvatar())
                .into(userPictureView);
    }
}
