package com.rupesh.audiohubapp.presenter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.model.CurrentDate;
import com.rupesh.audiohubapp.model.Project;

import java.util.HashMap;

public class ProjectsFragPresenter {

    // Declare Firebase database reference
    private DatabaseReference projectDatabaseRef;

    private DatabaseReference allProjectsRef;

    // Declare instance of Firebase authentication
    private FirebaseUser currentUser;

    private String uid;

    public ProjectsFragPresenter() {
        projectDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Projects");
        allProjectsRef = FirebaseDatabase.getInstance().getReference().child("All_Projects");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = currentUser.getUid();
    }

    public void createProject(String projectName) {
        CurrentDate currentDate = new CurrentDate();

        // Get the projectUid created in the Firebase database
        String pUid = projectDatabaseRef.push().getKey();

        // Write Project data into the Firebase database
        HashMap<String, Object> projectMap = new HashMap<>();
        projectMap.put("projectName", projectName );
        projectMap.put("createdOn", currentDate.getDate());
        projectMap.put("creatorId", uid);
        projectMap.put("projectId", pUid);
        // Set the project data in the Firebase Database
        projectDatabaseRef.child(pUid).setValue(projectMap);
        projectDatabaseRef.child(pUid).child("members").child(uid).setValue(true);

        HashMap<String, Object> allProjectMap = new HashMap<>();
        allProjectMap.put("projectName", projectName);
        allProjectMap.put("createdOn", currentDate.getDate());
        allProjectMap.put("creatorId", uid);
        allProjectMap.put("projectId", pUid);
        allProjectsRef.child(uid).child(pUid).setValue(allProjectMap);
    }

    public FirebaseRecyclerOptions<Project> queryData() {
        FirebaseRecyclerOptions<Project> options =
                new FirebaseRecyclerOptions.Builder<Project>()
                        .setQuery(allProjectsRef.child(uid), Project.class).build();
        return options;
    }
}
