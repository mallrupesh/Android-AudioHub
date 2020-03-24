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
import com.rupesh.audiohubapp.interfaces.InterfaceInvite;
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
    int state;
    public InterfaceInvite interfaceInvite;

    public NetworkHelper(Project project, User user) {
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        inviteDatabaseRef = FirebaseDatabase.getInstance().getReference().child(mINVITEREQUESTS);
        projectDatabaseRef = FirebaseDatabase.getInstance().getReference().child(mPROJECT);
        this.project = project;
        this.user = user;
    }

    public void sendRequest() {
        //state = RequestState.REQUEST_SENT;
        inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).child("request_type").setValue("sent");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").setValue("received");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("project_type").setValue(project.getProjectId());
    }


    public void cancelRequest() {

        //state = RequestState.NOT_IN_PROJECT;
        inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).removeValue();
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").removeValue();
        inviteDatabaseRef.child(user.getUid()).removeValue();
    }

    public void acceptRequest() {
        //state = RequestState.IN_PROJECT;
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user.getUid())){
                    String currentProject = dataSnapshot.child(user.getUid()).child("project_type").getValue().toString();
                    System.out.println(currentProject+"-----------------");
                    inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).removeValue();
                    inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").removeValue();
                    //List<String> memberList = new ArrayList<>();
                    //memberList.add(mCurrentUser.getUid());
                    projectDatabaseRef.child(currentProject).child("members").child(mCurrentUser.getUid()).setValue(true);

                    // members
                        // 1
                        // 2
                        // 3
                        // 4 Arraylist
                                //1.
                        // 5
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void leaveProject() {
       // state = RequestState.NOT_IN_PROJECT;
        projectDatabaseRef.child("Projects").child("member").removeValue();
    }

    public void addUserToProject(final String currentProject) {
        // check if project has already got members hashmap
        // if yes then add mCurrentUser.getID
        // else create a new hashmap and add mCurrentUSer to the array
        //projectDatabaseRef.child(currentProject).child("members").push().setValue(mCurrentUser.getUid());

        projectDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot members = dataSnapshot.child(currentProject).child("members");
                System.out.println(members +"*********************");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void searchUser() {
        // Point the invite database to the current user for dataSnapshot
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // If the current user has a child with other userId, check the for the REQUEST TYPE (sent/received)
                if (dataSnapshot.hasChild(user.getUid())) {
                    String request_type = dataSnapshot.child(user.getUid()).child("request_type").getValue().toString();
                   // String projectId = dataSnapshot.child(user.getUid()).child("project_type").getValue().toString();
                    if (request_type.equals("received")) {
                        // state, text, visibility
                        interfaceInvite.inviteNetworkCallback("ACCEPT INVITATION", RequestState.REQUEST_RECEIVED, View.VISIBLE);

                    } else {
                        if (request_type.equals("sent")) {
                            // state, text, visibility
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

