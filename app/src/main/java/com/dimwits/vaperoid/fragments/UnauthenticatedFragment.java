package com.dimwits.vaperoid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.activities.MainActivity;
import com.dimwits.vaperoid.activities.RegisterActivity;

/**
 * Created by farid on 2/26/17.
 */

public class UnauthenticatedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

            view.findViewById(R.id.login_register_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), RegisterActivity.class));
                }
            });

        return view;
    }
}
