package com.rupesh.audiohubapp.helper;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

public class NetworkHelper {

    static String mINVITEREQUESTS = "Invite_Requests";
    // Follow on
    static String mPROJECT = "Projects";
    DatabaseReference inviteDatabaseRef;
    DatabaseReference projectDatabaseRef;
    FirebaseUser mCurrentUser;
    User user;
    Project project;

    public NetworkHelper(Project project, User user) {
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        inviteDatabaseRef = FirebaseDatabase.getInstance().getReference().child(mINVITEREQUESTS);
        this.project = project;
        this.user = user;
    }

    public int sendRequest() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).child("request_type").setValue("sent");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").setValue("received");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("project_type").setValue(project.getProjectId());
        return RequestState.REQUEST_SENT;
    }


    public int cancelRequest() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).removeValue();
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").removeValue();
        return RequestState.NOT_IN_PROJECT;
    }

    public int acceptRequest(final String projectId) {
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user.getUid())){
                    //String currentProject = dataSnapshot.child(user.getUid()).child("project_type").getValue().toString();

                    projectDatabaseRef.child(projectId).child("members").push().setValue(mCurrentUser.getUid());
                    inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).removeValue();
                    inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").removeValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return RequestState.IN_PROJECT;
    }


    public int leaveProject() {
        projectDatabaseRef.child("Projects").child("member").removeValue();
        return RequestState.NOT_IN_PROJECT;
    }
}
