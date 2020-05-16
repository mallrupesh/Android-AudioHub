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

    public void searchUser() {
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> IDs = new ArrayList<>();
                final ArrayList<User> users = new ArrayList<>();

                for (DataSnapshot postData: dataSnapshot.getChildren()) {
                    String uid = postData.getKey();
                    IDs.add(uid);
                }

                for(String temp: IDs) {
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
