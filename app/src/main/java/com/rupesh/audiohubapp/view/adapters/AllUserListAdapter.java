package com.rupesh.audiohubapp.view.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rupesh.audiohubapp.InviteActivity;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUserListAdapter extends FirebaseRecyclerAdapter<User, AllUserListAdapter.AllUserListViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AllUserListAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AllUserListViewHolder holder, int position, @NonNull User model) {
        holder.singleUserName.setText(model.getName());
        holder.singleUserStatus.setText(model.getStatus());
        holder.setAllSingleUserImg(model.getImage());

        // Get the user Id by getting the position of the ViewHolder
        final String holderIdPosition = getRef(position).getKey();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inviteIntent = new Intent(v.getContext(), InviteActivity.class);
                inviteIntent.putExtra("user_id", holderIdPosition);
                v.getContext().startActivity(inviteIntent);
            }
        });
    }

    @NonNull
    @Override
    public AllUserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_all_users_row, parent, false);


        return new AllUserListViewHolder(view);
    }

    class AllUserListViewHolder extends RecyclerView.ViewHolder {

        CircleImageView singleUserImg;
        TextView singleUserName;
        TextView singleUserStatus;

        public AllUserListViewHolder(@NonNull View itemView) {
            super(itemView);
            singleUserImg = itemView.findViewById(R.id.all_user_single_img);
            singleUserName = itemView.findViewById(R.id.all_user_single_name);
            singleUserStatus = itemView.findViewById(R.id.all_user_single_status);
        }

        // Load all users images in the All User Activity if no user images load the default
        // user avatar
        public void setAllSingleUserImg(String image){

            Glide.with(itemView).load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                    .into(singleUserImg);
        }
    }



}
