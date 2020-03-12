package com.rupesh.audiohubapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rupesh.audiohubapp.model.Members;

public class ProjectListAdapter extends FirebaseRecyclerAdapter<Members, ProjectListAdapter.ProjectListViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProjectListAdapter(@NonNull FirebaseRecyclerOptions<Members> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProjectListViewHolder holder, int position, @NonNull Members model) {

        holder.projectName.setText(model.getName());
        holder.projectDate.setText(model.getHobby());
        holder.projectGenre.setText(model.getAddress());
    }

    @NonNull
    @Override
    public ProjectListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_project_row_item, parent, false);

        return new ProjectListViewHolder(view);
    }

    class ProjectListViewHolder extends RecyclerView.ViewHolder {

        // Declare TextView for the recycler card
        TextView projectName, projectDate, projectGenre;

        public ProjectListViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.textViewProjectName);
            projectDate = itemView.findViewById(R.id.textViewProjectDate);
            projectGenre = itemView.findViewById(R.id.textViewProjectGenre);
        }
    }
}
