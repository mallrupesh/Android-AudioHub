package com.rupesh.audiohubapp.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.User;

public class MemberListAdapter extends FirebaseRecyclerAdapter<User, MemberListAdapter.MemberListViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MemberListAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MemberListViewHolder holder, int position, @NonNull User model) {
        holder.memberName.setText(model.getName());
        holder.memberStatus.setText(model.getStatus());


    }

    @NonNull
    @Override
    public MemberListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_members_row_item, parent, false);
        return new MemberListViewHolder(view);
    }

    class MemberListViewHolder extends RecyclerView.ViewHolder {

        TextView memberName;
        TextView memberStatus;

        public MemberListViewHolder(@NonNull View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.textViewMembersName);
            memberStatus = itemView.findViewById(R.id.textViewMembersStatus);
        }
    }
}
