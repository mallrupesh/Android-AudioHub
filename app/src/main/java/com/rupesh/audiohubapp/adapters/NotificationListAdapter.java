package com.rupesh.audiohubapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    public interface OnItemClickListener{
        void onItemClicked(View v, User user);
    }

    private ArrayList<User> users;
    Context context;
    public OnItemClickListener onItemClickListener;

    public NotificationListAdapter(ArrayList<User> users, OnItemClickListener listener, Context context) {
        this.users = users;
        this.context = context;
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notification_row, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.username.setText(users.get(position).getName());
        holder.setAllSingleUserImg(users.get(position).getImage());

        // Implements the inner interface onItemClicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(v, users.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        // For Firebase asynchronous recall
        if (users == null)
            return 0;
        return users.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView userImg;
        private TextView username;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.notification_single_img);
            username = itemView.findViewById(R.id.notification_single_name);
        }

        /**
         * Load all users images in the AllUserActivity, if no user images available,
         * load the default user avatar
         */
        private void setAllSingleUserImg(String image){
            Glide.with(itemView).load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                    .into(userImg);
        }
    }
}
