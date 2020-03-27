package com.rupesh.audiohubapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rupesh.audiohubapp.model.ProjectFile;

public class FilesListAdapter extends FirebaseRecyclerAdapter<ProjectFile, FilesListAdapter.FilesViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FilesListAdapter(@NonNull FirebaseRecyclerOptions<ProjectFile> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FilesViewHolder holder, int position, @NonNull ProjectFile model) {

    }


    @NonNull
    @Override
    public FilesListAdapter.FilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class FilesViewHolder extends RecyclerView.ViewHolder {
        public FilesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
