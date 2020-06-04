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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder> {


    public interface OnItemSearchListener {
        void onItemSearched(View v, User user);
    }

    Context context;
    ArrayList<User> userList;
    private OnItemSearchListener listener;

    public SearchAdapter(Context context, ArrayList<User> list, OnItemSearchListener listener) {
        this.context = context;
        userList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_all_users_row, parent, false);
        return new SearchAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapterViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getName());
        holder.userStatus.setText(user.getStatus());
        holder.setAllSingleUserImg(user.getImage());

        // Implements the inner interface onItemClicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSearched(v, user);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class SearchAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView userStatus;
        private CircleImageView userImg;
        public SearchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.all_user_single_img);
            userName = itemView.findViewById(R.id.all_user_single_name);
            userStatus = itemView.findViewById(R.id.all_user_single_status);
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
