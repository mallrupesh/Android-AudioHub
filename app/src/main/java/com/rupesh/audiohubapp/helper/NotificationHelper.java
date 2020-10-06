package com.rupesh.audiohubapp.helper;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.interfaces.InterfaceRequestCallBack;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

/**
 * Helper class that retrieves the Users object from User node using Invite_Requests node
 * Performs double DataSnapShot tasks
 */
public class NotificationHelper {

    private static final String INVITE_REQUESTS = "Invite_Requests";
    private static final String USERS = "Users";

    private FirebaseUser mCurrentUser;
    private DatabaseReference inviteDatabaseRef;
    private DatabaseReference userDatabaseRef;
    public InterfaceRequestCallBack interfaceRequestCallBack;

    public NotificationHelper() {
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        inviteDatabaseRef = FirebaseDatabase.getInstance().getReference().child(INVITE_REQUESTS);
        userDatabaseRef = FirebaseDatabase.getInstance().getReference().child(USERS);
    }

    /**
     * Triggers read data from Invite_Requests node to read userIds and
     * uses the userIds to get respective Users objects
     */
    public void getUsers() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> IDs = new ArrayList<>();
                final ArrayList<User> users = new ArrayList<>();

                for (DataSnapshot postData: dataSnapshot.getChildren()) {
                    String uid = postData.getKey();
                    IDs.add(uid);
                }

                for(String temp: IDs) {
                    if(dataSnapshot.hasChild(temp)) {
                        String request_type = dataSnapshot.child(temp).child("request_type").getValue().toString();
                        if (request_type.equals("received")) {
                            userDatabaseRef.child(temp).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                                    User user = userSnapshot.getValue(User.class);
                                    users.add(user);
                                    interfaceRequestCallBack.mapRequest(users);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
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
