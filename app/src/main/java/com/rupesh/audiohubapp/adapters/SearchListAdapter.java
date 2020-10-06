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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * SearchListAdapter transforms the retrieved data into User objects
 * if appropriate and binds view components with the model(User) and displays
 * a list of the view items
 */
public class SearchListAdapter extends FirebaseRecyclerAdapter<User, SearchListAdapter.AllUserListViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */

    // Inner interface
    public interface OnItemClickListener{
        void onItemClicked(View v, User user);
    }

    private Context context;
    private OnItemClickListener listener;

    /**
     * Constructor
     * @param options FirebaseRecyclerOption<User>
     * @param listener interface instance OnItemClickListener
     * @param context App activity/ fragment context
     */
    public SearchListAdapter(@NonNull FirebaseRecyclerOptions<User> options, OnItemClickListener listener, Context context ) {
        super(options);
        this.context = context;
        this.listener = listener;
    }


    /**
     * Binds the components of the view holder with the model(User) data.
     * @param holder refers to the view holder that wraps the view components
     * @param position refers to the position of the view holder in the recycler view list
     * @param model refers to the Project model
     */
    @Override
    protected void onBindViewHolder(@NonNull final AllUserListViewHolder holder, final int position, @NonNull final User model) {
        holder.singleUserName.setText(model.getName());
        holder.singleUserStatus.setText(model.getStatus());
        holder.setAllSingleUserImg(model.getImage());

        // Implements the inner interface onItemClicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(v, model);
            }
        });
    }


    /**
     * Inflates each all_user_row layout upon the start of SearchFragment
     * @param parent refers to the RecyclerView in SearchActivity that wraps
     *               each all_user_row layout
     * @param viewType refers to the view type of the item at position for the
     *                 purpose of view recycling
     * @return specific view holder that wraps the components in layout_all_user_row
     */
    @NonNull
    @Override
    public AllUserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_all_users_row, parent, false);
        return new AllUserListViewHolder(view);
    }

    /**
     * Declare the view components to be displayed
     */
    class AllUserListViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView singleUserImg;
        private TextView singleUserName;
        private TextView singleUserStatus;

        private AllUserListViewHolder(@NonNull View itemView) {
            super(itemView);
            singleUserImg = itemView.findViewById(R.id.all_user_single_img);
            singleUserName = itemView.findViewById(R.id.all_user_single_name);
            singleUserStatus = itemView.findViewById(R.id.all_user_single_status);
        }

        /**
         * Load all users images in the AllUserActivity, if no user images available,
         * load the default user avatar
         */
        private void setAllSingleUserImg(String image){
            Glide.with(itemView).load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                    .into(singleUserImg);
        }
    }
}
