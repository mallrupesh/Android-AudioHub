package com.rupesh.audiohubapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.CurrentDate;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.view.adapters.ProjectListAdapter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends Fragment {

    View rootView;
    Context context;
    private EditText mProjectName;
    private Button mProjectBtn;
    private String projectName;
    private String currentDate;
    private RecyclerView recyclerView;

    // Declare instance of Firebase authentication
    private FirebaseAuth mAuth;
    // Declare Firebase database reference
    private DatabaseReference mDatabase;

    ProjectListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_project, container, false);

        // Init View components
        recyclerView = rootView.findViewById(R.id.recycleListViewProject);
        mProjectName = rootView.findViewById(R.id.projects_fragment_newProjectTextView);
        mProjectBtn = rootView.findViewById(R.id.projects_fragment_btnAdd);
        mAuth = FirebaseAuth.getInstance();
        addProject();
        initUI();

        return rootView;
    }

    // Add Project on buttonClicked
    private void addProject(){
        mProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectName = mProjectName.getText().toString();
                CurrentDate currentDate = new CurrentDate();
                Project project = new Project(projectName, currentDate.getDate());
                //currentDate = project.getCreatedOn();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUser.getUid();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Projects");
                //mDatabase.push().setValue(uid);

                HashMap<String, Object> projectMap = new HashMap<>();
                projectMap.put("projectName", project.getProjectName() );
                projectMap.put("createdOn", project.getCreatedOn());
                projectMap.put("creatorId", uid);
                ArrayList<String> list = new ArrayList<>();
                list.add("fasfsg");
                list.add("fsaassdsd");
                list.add("cccc");
                projectMap.put("members", list);

                mDatabase.push().setValue(projectMap);



                Toast.makeText(getContext(), "Project created successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Init the recyclerView and set the FirebaseRecyclerViewAdapter to it
    private void initUI(){
        recyclerView = rootView.findViewById(R.id.recycleListViewProject);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        FirebaseRecyclerOptions<Project> options =
                new FirebaseRecyclerOptions.Builder<Project>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Projects").orderByChild("creatorId")
                                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()), Project.class)
                        .build();

        adapter = new ProjectListAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
