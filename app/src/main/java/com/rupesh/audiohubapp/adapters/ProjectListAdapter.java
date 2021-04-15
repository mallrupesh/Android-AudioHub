package com.rupesh.audiohubapp.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.activities.MainProjectActivity;
import com.rupesh.audiohubapp.fragments.ProjectsFragment;
import com.rupesh.audiohubapp.model.Project;

/**
 * ProjectListAdapter transforms the retrieved data into Project objects
 * if appropriate and binds view components with the model(Project) and displays
 * a list of the view items
 */
public class ProjectListAdapter extends FirebaseRecyclerAdapter<Project, ProjectListAdapter.ProjectListViewHolder> {

    ProjectsFragment projectsFragment;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options @param options FirebaseRecyclerOption<Project>
     */
    public ProjectListAdapter(@NonNull FirebaseRecyclerOptions<Project> options, ProjectsFragment projectsFragment) {
        super(options);
        this.projectsFragment = projectsFragment;
    }

    /**
     * Binds the components of the view holder with the model(Project) data.
     * @param holder refers to the view holder that wraps the view components
     * @param position refers to the position of the view holder in the recycler view list
     * @param model refers to the Project model
     */
    @Override
    protected void onBindViewHolder(@NonNull ProjectListViewHolder holder, int position, @NonNull final Project model) {
        holder.projectName.setText(model.getProjectName());
        holder.projectDate.setText(model.getCreatedOn());

        // Get the project Id by getting the position of the ViewHolder
        //final String holderProjectIdPosition = getRef(position).getKey();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainProjectActivity and pass Project model
                Intent mainProjectIntent = new Intent(v.getContext(), MainProjectActivity.class);
                mainProjectIntent.putExtra("project", model);
                v.getContext().startActivity(mainProjectIntent);
            }
        });

        // On long click of list item, open the Alert dialog box and display option to delete
        // the item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            String proName = getItem(position).getProjectName();
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(projectsFragment.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Confirm deletion of: " + proName);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        projectsFragment.removeProject(proName);
                        Toast.makeText(projectsFragment.getContext(), "Project Deleted", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return true;
            }
        });
    }


    /**
     * Inflates each layout_project_row_item layout upon the start of ProjectFragment
     * @param parent refers to the RecyclerView in ProjectFragment that wraps
     *               each layout_project_row_item
     * @param viewType refers to the view type of the item at position for the
     *                 purpose of view recycling
     * @return specific view holder that wraps the components layout_project_row_item
     */
    @NonNull
    @Override
    public ProjectListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_project_row_item, parent, false);

        return new ProjectListViewHolder(view);
    }


    /**
     * Declare the view components to be displayed
     */
    class ProjectListViewHolder extends RecyclerView.ViewHolder {
        // Declare TextView for the recycler card
        TextView projectName;
        TextView projectDate;

        public ProjectListViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.textViewProjectName);
            projectDate = itemView.findViewById(R.id.textViewProjectDate);
        }
    }
}
