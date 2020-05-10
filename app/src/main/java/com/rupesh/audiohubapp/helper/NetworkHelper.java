package com.rupesh.audiohubapp.helper;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.interfaces.InterfaceDecline;
import com.rupesh.audiohubapp.interfaces.InterfaceInvite;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

public class NetworkHelper {

    private static final String INVITE_REQUESTS = "Invite_Requests";
    private static final String PROJECTS = "Projects";
    private static final String USERS = "Users";
    private static final String PROJECT_MEMBERS = "Project_Members";

    private DatabaseReference inviteDatabaseRef;
    private DatabaseReference projectDatabaseRef;
    private DatabaseReference usersDatabaseRef;
    private DatabaseReference membersDatabaseRef;
    private FirebaseUser mCurrentUser;
    private User user;
    private Project project;
    public InterfaceInvite interfaceInvite;
    public InterfaceDecline interfaceDecline;

    public NetworkHelper(Project project, User user) {
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        inviteDatabaseRef = FirebaseDatabase.getInstance().getReference().child(INVITE_REQUESTS);
        projectDatabaseRef = FirebaseDatabase.getInstance().getReference().child(PROJECTS);
        usersDatabaseRef = FirebaseDatabase.getInstance().getReference().child(USERS);
        membersDatabaseRef = FirebaseDatabase.getInstance().getReference().child(PROJECT_MEMBERS);
        this.project = project;
        this.user = user;
    }

    public void sendRequest() {

        // TODO check if the receiver is already in the project
        inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).child("request_type").setValue("sent");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").setValue("received");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("project_type").setValue(project.getProjectId());
    }


    public void cancelRequest() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).removeValue();
        inviteDatabaseRef.child(user.getUid()).removeValue();
    }

    public void acceptRequest() {
        // Get the project id from the Invite_Requests database that can be used to set project/ members
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user.getUid())){
                    final String projectId = dataSnapshot.child(user.getUid()).child("project_type").getValue().toString();
                    inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).removeValue();
                    inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").removeValue();
                    projectDatabaseRef.child(projectId).child("members").child(mCurrentUser.getUid()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void declineRequest() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).removeValue();
        inviteDatabaseRef.child(user.getUid()).removeValue();
    }


    public void searchUser() {
        // Point the invite database to the current user for dataSnapshot
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // If the current user has a child with other userId, check the for the REQUEST TYPE (sent/received)
                if (dataSnapshot.hasChild(user.getUid())) {

                    String request_type = dataSnapshot.child(user.getUid()).child("request_type").getValue().toString();
                    if (request_type.equals("received")) {

                        // Pass text, state and visibility
                        interfaceInvite.inviteNetworkCallback("ACCEPT INVITATION", RequestState.REQUEST_RECEIVED, View.VISIBLE);
                        interfaceDecline.declineNetworkCallback("DECLINE INVITATION", RequestState.REQUEST_RECEIVED, View.VISIBLE);

                    } else {

                        if (request_type.equals("sent")) {
                            // Pass text, state and visibility
                            interfaceInvite.inviteNetworkCallback("CANCEL REQUEST", RequestState.REQUEST_SENT, View.VISIBLE);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}

