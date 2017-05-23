package com.dimwits.vaperoid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.requests.entities.UserEntity;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView userPicture;
        private final TextView username;
        private final TextView name;
        private final TextView about;

        public ViewHolder(View itemView) {
            super(itemView);
            userPicture = (ImageView) itemView.findViewById(R.id.profile_picture);
            username = (TextView) itemView.findViewById(R.id.profile_username);
            name = (TextView) itemView.findViewById(R.id.profile_name);
            about = (TextView) itemView.findViewById(R.id.profile_about);
        }

        public void bind(UserEntity user) {

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
