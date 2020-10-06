package com.rupesh.audiohubapp.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.rupesh.audiohubapp.fragments.FilesFragment;
import com.rupesh.audiohubapp.model.File;

public class FilesListAdapter extends FirebaseRecyclerAdapter<File, FilesListAdapter.FilesViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param
     */


    public interface OnItemClickListener {
        void onItemClicked(File file);
    }

    private FilesFragment filesFragment;
    private OnItemClickListener listener;

    public FilesListAdapter(@NonNull FirebaseRecyclerOptions<File> options, OnItemClickListener listener, FilesFragment filesFragment) {
        super(options);
        this.listener = listener;
        this.filesFragment = filesFragment;
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            String fileName = getItem(position).getName();
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(filesFragment.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Confirm deletion of: " + fileName);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filesFragment.removeFile(fileName);
                        Toast.makeText(filesFragment.getContext(), "Files Deleted", Toast.LENGTH_LONG).show();
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
