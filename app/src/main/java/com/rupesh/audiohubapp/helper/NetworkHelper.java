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
import com.rupesh.audiohubapp.model.RequestState;
import com.rupesh.audiohubapp.model.User;

import java.util.HashMap;


/**
 * NetworkHelper class handles the methods required for Invitation request send/accept
 * cancel/decline mechanism
 */
public class NetworkHelper {

    private static final String INVITE_REQUESTS = "Invite_Requests";
    private static final String PROJECTS = "Projects";
    private static final String ALL_PROJECTS = "All_Projects";

    private DatabaseReference inviteDatabaseRef;
    private DatabaseReference projectDatabaseRef;
    private DatabaseReference allProjectDatabaseRef;
    private FirebaseUser mCurrentUser;

    private User user;
    private Project project;

    public InterfaceInvite interfaceInvite;
    public InterfaceDecline interfaceDecline;

    /**
     * @param project Project model
     * @param user User model object
     */
    public NetworkHelper(Project project, User user) {
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        inviteDatabaseRef = FirebaseDatabase.getInstance().getReference().child(INVITE_REQUESTS);
        projectDatabaseRef = FirebaseDatabase.getInstance().getReference().child(PROJECTS);
        allProjectDatabaseRef = FirebaseDatabase.getInstance().getReference().child(ALL_PROJECTS);
        this.project = project;
        this.user = user;
    }

    /**
     * Triggers write data to database node Invite_Requests when current user sends request to
     * the user selected in the Search list SearchFragment
     */
    public void sendRequest() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).child("request_type").setValue("sent");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").setValue("received");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("project_type").setValue(project.getProjectId());
    }

    /**
     * Triggers update data to database node Invite_Requests when current user cancels request to
     * the user selected in the Search list in SearchFragment
     */
    public void cancelRequest() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).removeValue();
        inviteDatabaseRef.child(user.getUid()).removeValue();
    }

    /**
     * Triggers update data to database node Invite_Requests.
     * And write data to Projects and All_Projects nodes when current user accepts the invitation request
     *
     * Retrieves the invited Project's Id from the Invite_Request node and uses it to add user to the
     * project member list
     *
     * And also uses the Project Id to get Projects data to write to All_Projects node to include the project
     * to that specific user who accepted the invitation request
     */
    public void acceptRequest() {
        // Get the project id from the Invite_Requests database that can be used to set project/ members
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user.getUid())){
                    // Dismantle Invite_Requests node
                    // Add user to the project member
                    final String projectId = dataSnapshot.child(user.getUid()).child("project_type").getValue().toString();
                    inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).removeValue();
                    inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").removeValue();
                    projectDatabaseRef.child(projectId).child("members").child(mCurrentUser.getUid()).setValue(true);

                    projectDatabaseRef.child(projectId).addListenerForSingleValueEvent(new ValueEventListener() {
                        // Retrieve project data using the projectId
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String projectName = dataSnapshot.child("projectName").getValue().toString();
                            final String projectDate = dataSnapshot.child("createdOn").getValue().toString();
                            final String projectCreator = dataSnapshot.child("creatorId").getValue().toString();

                            // Data de-normalization
                            // Store project data in the All-project database node under specific userId
                            HashMap<String, Object> allProjectMap = new HashMap<>();
                            allProjectMap.put("projectName", projectName);
                            allProjectMap.put("createdOn", projectDate);
                            allProjectMap.put("creatorId", projectCreator);
                            allProjectMap.put("projectId", projectId);
                            allProjectDatabaseRef.child(mCurrentUser.getUid()).child(projectId).setValue(allProjectMap);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * On request decline, delete the Invite_Request node for the particular invitation
     */
    public void declineRequest() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).removeValue();
        inviteDatabaseRef.child(user.getUid()).removeValue();
    }


    /**
     * Hits Invite_Request node and checks for the REQUEST_TYPE(send/received) under userId node
     * if REQUEST_TYPE = received ==> call interfaceInvite, interfaceDecline
     * if REQUEST_TYPE = sent ==> call interfaceInvite
     */
    public void handleInvitationRequest() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If the current user has a child as another userId, check for the REQUEST TYPE (sent/received)
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

