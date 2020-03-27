package com.rupesh.audiohubapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.File;

public class MyFilesListAdapter extends FirebaseRecyclerAdapter<File, MyFilesListAdapter.MyFilesViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyFilesListAdapter(@NonNull FirebaseRecyclerOptions<File> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyFilesListAdapter.MyFilesViewHolder holder, int position, @NonNull File model) {
        holder.fileName.setText(model.getName());
        holder.fileDate.setText(model.getCreatedOn());
    }

    @NonNull
    @Override
    public MyFilesListAdapter.MyFilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_my_files_row_item, parent, false);
        return  new MyFilesViewHolder(view);
    }

     class MyFilesViewHolder extends RecyclerView.ViewHolder {
        // Declare TextView for the recycler card
        TextView fileName;
        TextView fileDate;


        public MyFilesViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.textViewFileName);
            fileDate = itemView.findViewById(R.id.textViewFileDate);
        }
    }
}
