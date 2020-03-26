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
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

public class MemberListAdapter extends FirebaseRecyclerAdapter<Project, MemberListAdapter.MemberListViewHolder> {

    //DatabaseReference projectRef;
    //Project project;
    private User user;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MemberListAdapter(@NonNull FirebaseRecyclerOptions<Project> options) {
        super(options);
        //this.project = project;
        //projectRef = FirebaseDatabase.getInstance().getReference().child("Projects");
        user = new User();
    }

    @Override
    protected void onBindViewHolder(@NonNull MemberListViewHolder holder, int position, @NonNull Project model) {

        holder.memberName.setText(model.getProjectName());
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

        public MemberListViewHolder(@NonNull View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.textViewMembersName);
        }
    }
}
