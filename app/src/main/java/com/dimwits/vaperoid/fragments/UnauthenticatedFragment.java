package com.dimwits.vaperoid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.activities.RegisterActivity;
import com.dimwits.vaperoid.requests.AuthenticationRequest;
import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;

/**
 * Created by farid on 2/26/17.
 */

public class UnauthenticatedFragment extends Fragment implements ResponseListener {
    private Integer taskId = 0;
    private EditText loginField;
    private EditText passwordField;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginField = (EditText) view.findViewById(R.id.login_login);
        passwordField = (EditText) view.findViewById(R.id.login_password);

        view.findViewById(R.id.login_register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginField.getText().toString();
                String password = passwordField.getText().toString();
                AuthenticationRequest authenticator = new AuthenticationRequest();
                try {
                    authenticator.authenticate(UnauthenticatedFragment.this, login, password);
                } catch (ViolatedConstraintsException e) {
                    Toast.makeText(UnauthenticatedFragment.this.getContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onResponseFinished(String response, boolean isSuccessful) {
        if (isSuccessful) {
            Toast.makeText(getContext(),
                    response,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this.getContext(), "Trouble, trouble, trouble, trouble!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
