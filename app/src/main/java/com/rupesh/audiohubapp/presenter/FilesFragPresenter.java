package com.rupesh.audiohubapp.presenter;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.fragments.FilesFragment;
import com.rupesh.audiohubapp.model.File;


/**
 * Files fragment presenter, handles Firebase operations
 */
public class FilesFragPresenter {
    private final static String PROJECT_FILES = "Project_Files";
    private DatabaseReference projectFilesDataRef;
    private FilesFragment filesFragment;

    public FilesFragPresenter(FilesFragment filesFragment) {
        this.filesFragment = filesFragment;
        projectFilesDataRef = FirebaseDatabase.getInstance().getReference().child(PROJECT_FILES);
    }

    /**
     * Firebase query to Project_Files database to read list of files
     * @return FirebaseRecycler options
     */
    public FirebaseRecyclerOptions<File> queryData() {
        FirebaseRecyclerOptions<File> options =
                new FirebaseRecyclerOptions.Builder<File>()
                        .setQuery(projectFilesDataRef.child(filesFragment.getProject().getProjectId()), File.class).build();
        return options;
    }

    /**
     * Delete selected file in the File list
     * @param title
     */
    public void deleteFile(String title) {
        Query query = projectFilesDataRef.child(filesFragment.getProject().getProjectId()).orderByChild ("name").equalTo(title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postData: dataSnapshot.getChildren()) {
                    postData.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
