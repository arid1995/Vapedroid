package com.dimwits.vaperoid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.activities.MainActivity;
import com.dimwits.vaperoid.activities.RegisterActivity;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.network.NetworkHelper;

/**
 * Created by farid on 2/26/17.
 */

public class UnauthenticatedFragment extends Fragment implements ResponseListener {
    Integer taskId = 0;

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
                taskId = NetworkHelper.getInstance().sendRequest(UnauthenticatedFragment.this,
                        NetworkHelper.POST, "shit",
                        "https://yandex.ru");
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
