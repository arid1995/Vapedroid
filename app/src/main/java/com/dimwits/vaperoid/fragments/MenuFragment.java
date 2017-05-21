package com.dimwits.vaperoid.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dimwits.vaperoid.R;

/**
 * Created by farid on 2/26/17.
 */

public class MenuFragment extends Fragment {
    private Button exitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        exitButton = (Button) view.findViewById(R.id.menu_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSessionId();
            }
        });
        return view;
    }

    private void deleteSessionId() {
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(
                this.getContext().getString(R.string.shared_pref_file),
                Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(UnauthenticatedFragment.SESSION_ID_KEY);
        editor.apply();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.menu_login_container, new UnauthenticatedFragment());
        transaction.commitAllowingStateLoss();
    }
}
