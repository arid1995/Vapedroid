package com.dimwits.vaperoid.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.activities.RegistrationActivity;
import com.dimwits.vaperoid.requests.AuthenticationRequest;
import com.dimwits.vaperoid.requests.entities.SessionEntity;
import com.dimwits.vaperoid.requests.exceptions.ViolatedConstraintsException;
import com.dimwits.vaperoid.requests.exceptions.WrongCredentialsException;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.google.gson.Gson;

public class UnauthenticatedFragment extends Fragment implements ResponseListener {
    public static final String SESSION_ID_KEY = "sessionId";
    private EditText loginField;
    private EditText passwordField;
    private AuthenticationRequest authenticator = new AuthenticationRequest();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginField = (EditText) view.findViewById(R.id.login_login);
        passwordField = (EditText) view.findViewById(R.id.login_password);

        view.findViewById(R.id.login_register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!authenticator.isFinished()) {
                    Toast.makeText(getContext(), "Be patient please", Toast.LENGTH_SHORT).show();
                    return;
                }
                String login = loginField.getText().toString();
                String password = passwordField.getText().toString();
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
            try {
                writeSessionInPrefs(response);
            } catch (WrongCredentialsException e) {
                Toast.makeText(this.getContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                return;
            }
            authenticator.requestFinished();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menu_login_container, new MenuFragment());
            transaction.commitAllowingStateLoss();
            return;
        }

        Toast.makeText(this.getContext(), "Something is wrong with the connection",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        authenticator.stopProcess();
        super.onStop();
    }

    private void writeSessionInPrefs(String sessionJsonString) throws WrongCredentialsException {
        Gson gson = new Gson();
        SessionEntity session = gson.fromJson(sessionJsonString, SessionEntity.class);
        String sessionId = session.getSessionId();
        if (sessionId == null) {
            throw new WrongCredentialsException("Wrong credentials");
        }
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(
                this.getContext().getString(R.string.shared_pref_file),
                Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_ID_KEY, sessionId);
        editor.apply();
    }
}
