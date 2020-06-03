package com.rupesh.audiohubapp.adapters;

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
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.Comment;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerListAdapter extends FirebaseRecyclerAdapter<Comment, PlayerListAdapter.CommentListViewHolder> {

    public PlayerListAdapter(@NonNull FirebaseRecyclerOptions<Comment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PlayerListAdapter.CommentListViewHolder holder, int position, @NonNull Comment model) {
        holder.commenterName.setText(model.getCommenter());
        holder.commentTxt.setText(model.getComment());
        holder.setCommenterImg(model.getImage());
    }

    @NonNull
    @Override
    public PlayerListAdapter.CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_comment, parent, false);
        return new CommentListViewHolder(view);
    }

    class CommentListViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView commenterImg;
        private TextView commenterName;
        private TextView commentTxt;
        private TextView dot;

        public CommentListViewHolder(@NonNull View itemView) {
            super(itemView);

            commenterImg = itemView.findViewById(R.id.player_commenter_img);
            commenterName = itemView.findViewById(R.id.player_commenter_name);
            commentTxt = itemView.findViewById(R.id.player_comment);
            dot = itemView.findViewById(R.id.player_dot);
        }

        /**
         * Load all users images in the AllUserActivity, if no user images available,
         * load the default user avatar
         */
        private void setCommenterImg(String image){
            Glide.with(itemView).load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                    .into(commenterImg);
        }
    }

}
