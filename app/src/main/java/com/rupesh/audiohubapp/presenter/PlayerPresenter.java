package com.rupesh.audiohubapp.presenter;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.activities.PlayerActivity;
import com.rupesh.audiohubapp.model.File;
import com.rupesh.audiohubapp.model.Project;

public class PlayerPresenter {

    private static final String PROJECT_FILES = "Project_Files";
    private DatabaseReference projectFilesDataRef;
    private PlayerActivity playerActivity;

    private Project project;
    private File file;

    public PlayerPresenter(PlayerActivity playerActivity) {
        projectFilesDataRef = FirebaseDatabase.getInstance().getReference().child(PROJECT_FILES);
        this.playerActivity = playerActivity;
        this.project = playerActivity.getProject();
        this.file = playerActivity.getFile();
    }

    public void playPauseFile() {
        projectFilesDataRef.child(project.getProjectId()).child(file.getFileId()).addListenerForSingleValueEvent(new ValueEventListener() {
            String fileUrl;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fileUrl = dataSnapshot.child("fileUrl").getValue().toString();
                playerActivity.initPlayer(fileUrl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
