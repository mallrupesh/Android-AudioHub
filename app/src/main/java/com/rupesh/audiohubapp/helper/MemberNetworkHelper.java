package com.rupesh.audiohubapp.helper;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.interfaces.InterfaceMemberCallback;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

public class MemberNetworkHelper {

    private static final String PROJECTS = "Projects";

    private static final String USERS = "Users";

    private DatabaseReference projectDatabaseRef;
    private DatabaseReference userDatabaseRef;

    public InterfaceMemberCallback interfaceMemberCallback;

    private Project project;


    public MemberNetworkHelper(Project project) {
        projectDatabaseRef = FirebaseDatabase.getInstance().getReference().child(PROJECTS);
        userDatabaseRef = FirebaseDatabase.getInstance().getReference().child(USERS);
        this.project = project;
    }

    public void getMember() {
        projectDatabaseRef.child(project.getProjectId()).child("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> IDs = new ArrayList<>();
                final ArrayList<User> members = new ArrayList<>();

                for (DataSnapshot postData: dataSnapshot.getChildren()) {
                    String uid = postData.getKey();
                    IDs.add(uid);
                }

                for (String temp: IDs) {
                    userDatabaseRef.child(temp).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            User member = userSnapshot.getValue(User.class);
                            members.add(member);

                            interfaceMemberCallback.mapUser(members);
                           // Log.d("USERS_", "Users " + members);
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
