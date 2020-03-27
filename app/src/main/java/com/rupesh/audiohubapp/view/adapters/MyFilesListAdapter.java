package com.rupesh.audiohubapp.view.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rupesh.audiohubapp.model.Files;

public class MyFilesListAdapter extends FirebaseRecyclerAdapter<Files, MyFilesListAdapter.MyFilesViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyFilesListAdapter(@NonNull FirebaseRecyclerOptions<Files> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyFilesListAdapter.MyFilesViewHolder holder, int position, @NonNull Files model) {

    }

    @NonNull
    @Override
    public MyFilesListAdapter.MyFilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class MyFilesViewHolder extends RecyclerView.ViewHolder {
        public MyFilesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
