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
import com.rupesh.audiohubapp.model.File;

public class FilesListAdapter extends FirebaseRecyclerAdapter<File, FilesListAdapter.FilesViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public interface OnItemClickListener {
        void onItemClicked(File file);
    }

    private DatabaseReference projectFilesDataRef;
    private OnItemClickListener listener;

    public FilesListAdapter(@NonNull FirebaseRecyclerOptions<File> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
        projectFilesDataRef = FirebaseDatabase.getInstance().getReference().child("Project_Files");
    }

    @Override
    protected void onBindViewHolder(@NonNull final FilesViewHolder holder, int position, @NonNull final File model) {

        holder.fileName.setText(model.getName());
        holder.fileDate.setText(model.getCreatedOn());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(model);
            }
        });
    }


    @NonNull
    @Override
    public FilesListAdapter.FilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_file_row_item, parent, false);
        return  new FilesViewHolder(view);
    }

    public class FilesViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;
        TextView fileDate;

        public FilesViewHolder(@NonNull View itemView) {
            super(itemView);

            fileName = itemView.findViewById(R.id.textViewFileNamePro);
            fileDate = itemView.findViewById(R.id.textViewFileDatePro);
        }
    }
}
