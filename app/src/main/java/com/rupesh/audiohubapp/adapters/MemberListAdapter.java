package com.rupesh.audiohubapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.ProjectMember;

public class MemberListAdapter extends FirebaseRecyclerAdapter<ProjectMember, MemberListAdapter.MemberListViewHolder> {

    DatabaseReference memberDatabaseRef;
    private Project project;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MemberListAdapter(@NonNull FirebaseRecyclerOptions<ProjectMember> options, Project project) {
        super(options);
        this.project = project;
        memberDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Project_Members").child(project.getProjectId());
    }

    @Override
    protected void onBindViewHolder(@NonNull final MemberListViewHolder holder, int position, @NonNull final ProjectMember model) {

       /* memberDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getChildren().toString().trim();
                holder.memberName.setText(memberDatabaseRef.child(name).getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


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
