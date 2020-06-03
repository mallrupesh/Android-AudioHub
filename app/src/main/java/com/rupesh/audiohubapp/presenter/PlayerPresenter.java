package com.rupesh.audiohubapp.presenter;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.activities.PlayerActivity;
import com.rupesh.audiohubapp.model.Comment;
import com.rupesh.audiohubapp.model.CurrentDate;
import com.rupesh.audiohubapp.model.File;
import com.rupesh.audiohubapp.model.Project;

import java.util.HashMap;

public class PlayerPresenter {

    private static final String PROJECT_FILES = "Project_Files";
    private static final String FILE_COMMENTS = "File_Comments";
    private static final String USERS = "Users";
    private DatabaseReference projectFilesDataRef;
    private DatabaseReference filesCommentDataRef;
    private DatabaseReference userDataRef;
    private FirebaseUser currentUser;
    private PlayerActivity playerActivity;

    private Project project;
    private File file;

    public PlayerPresenter(PlayerActivity playerActivity) {
        projectFilesDataRef = FirebaseDatabase.getInstance().getReference().child(PROJECT_FILES);
        filesCommentDataRef = FirebaseDatabase.getInstance().getReference().child(FILE_COMMENTS);
        userDataRef = FirebaseDatabase.getInstance().getReference().child(USERS);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
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

    public void writeToComment(String comment, String name, String img) {
        CurrentDate currentDate = new CurrentDate();

        // Get the commentId created in the Firebase database
        String commentId = filesCommentDataRef.push().getKey();

        // Write Project data into the Firebase database
        HashMap<String, Object> commentMap = new HashMap<>();
        commentMap.put("createdOn", currentDate.getDate());
        commentMap.put("commentId", commentId);
        commentMap.put("comment", comment);
        commentMap.put("image", img);
        commentMap.put("commenter", name);

        filesCommentDataRef.child(file.getFileId()).child(commentId).setValue(commentMap);
    }

    public void inputComment(String comment) {
        userDataRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child("name").getValue().toString();
                String imgUrl = dataSnapshot.child("image").getValue().toString();

                writeToComment(comment, userName, imgUrl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public FirebaseRecyclerOptions<Comment> queryData() {
        FirebaseRecyclerOptions<Comment> options =
                new FirebaseRecyclerOptions.Builder<Comment>()
                        .setQuery(filesCommentDataRef.child(file.getFileId()), Comment.class).build();
        return options;
    }
}
