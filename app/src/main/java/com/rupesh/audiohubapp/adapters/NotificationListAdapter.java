package com.rupesh.audiohubapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    private ArrayList<User> users;
    Context context;

    public NotificationListAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
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
        //holder.projectName.setText(project.getProjectName());
    }


    @Override
    public int getItemCount() {
        // For Firebase asynchronous recall
        if (users == null)
            return 0;
        return users.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView projectName;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.notification_single_name);
            //projectName = itemView.findViewById(R.id.notification_project_name);
        }
    }
}
