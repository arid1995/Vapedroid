package com.dimwits.vaperoid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.activities.MainActivity;
import com.dimwits.vaperoid.activities.RegisterActivity;
import com.dimwits.vaperoid.utils.helpers.ServiceHelper;

/**
 * Created by farid on 2/26/17.
 */

public class UnauthenticatedFragment extends Fragment implements ServiceHelper.ResultListener {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

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
                ServiceHelper.getInstance(view.getContext()).getText(view.getContext(),
                        "http://mail.ru", UnauthenticatedFragment.this);
            }
        });

        return view;
    }



    @Override
    public void handleResponse(String response) {
        Toast.makeText(getContext(), response.substring(0, 256), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        ServiceHelper.getInstance(getContext()).removeListener();
    }
}
