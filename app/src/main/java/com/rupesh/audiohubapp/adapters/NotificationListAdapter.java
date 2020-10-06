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


/**
 * NotificationListAdapter transforms the retrieved data into User objects
 * if appropriate and binds view components with the model(User) and displays
 * a list of the view items
 */
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

    /**
     * Inflates each layout_notification_row layout upon the start of NotificationFragment
     * @param parent refers to the RecyclerView in ProjectFragment that wraps
     *               each layout_notification_row
     * @param viewType refers to the view type of the item at position for the
     *                 purpose of view recycling
     * @return specific view holder that wraps the components layout_notification_row
     */
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notification_row, parent, false);
        return new NotificationViewHolder(view);
    }

    /**
     * Binds the components of the view holder with the model(Project) data.
     * @param holder refers to the view holder that wraps the view components
     * @param position refers to the position of the view holder in the recycler view list
     */
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

    /**
     * Handle Firebase asynchronous recall
     * @return size of users array
     */
    @Override
    public int getItemCount() {
        if (users == null)
            return 0;
        return users.size();
    }

    /**
     * Declare the view components to be displayed
     */
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
