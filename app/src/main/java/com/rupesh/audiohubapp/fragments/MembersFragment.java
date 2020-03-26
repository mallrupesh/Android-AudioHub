package com.rupesh.audiohubapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.activities.AllUsersActivity;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.view.adapters.MemberListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */

// TODO List
//  1. list of user who are in the project
//  2. when pressed on the user then invite dialog box sud open
//  3. Current user sud be able to delete the user from the project
//  4. The arraylist of the member sud be passed to AllUserActivity as array
public class MembersFragment extends Fragment {

    private View rootView;
    private Context context;
    private Button mAddMembersBtn;

    private RecyclerView projectMemberRecyclerView;
    private MemberListAdapter memberListAdapter;
    private Project project;


    private DatabaseReference mDatabaseProject;
    FirebaseUser mCurrentUser;


    public MembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_members, container, false);

        //projectMemberRecyclerView = rootView.findViewById(R.id.recycleListViewProjectMembers);
        mAddMembersBtn = rootView.findViewById(R.id.members_fragment_add_btn);

        // Get Project model object from MainProjectActivity through projectPagerSectionsAdapter
        project = (Project) getArguments().getSerializable("project");

        mDatabaseProject = FirebaseDatabase.getInstance().getReference().child("Projects");


        initUI();


        /*mDatabaseProject.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Project> memberList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Project member = postSnapshot.getValue(Project.class);
                    memberList.add(member);
                    //member.getMembers();
                    textView.setText(member.toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



        // Setup OnClickListener to ADDMEMBER btn and send projectID upon the navigation to
        // AllUserActivity with the request code 2
        mAddMembersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allUsersActivity = new Intent(rootView.getContext(), AllUsersActivity.class);
                allUsersActivity.putExtra("project", project);
                startActivity(allUsersActivity);
            }
        });

        return rootView;
    }

    private void initUI() {

        projectMemberRecyclerView = rootView.findViewById(R.id.recycleListViewProjectMembers);
        projectMemberRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        /*FirebaseRecyclerOptions<Project> options = new FirebaseRecyclerOptions.Builder<Project>()
                .setQuery(mDatabaseProject.child(project.getProjectId()), Project.class).build();*/
        FirebaseRecyclerOptions<Project> options =
                new FirebaseRecyclerOptions.Builder<Project>()
                        .setQuery(mDatabaseProject.orderByChild("member"), Project.class).build();
        memberListAdapter = new MemberListAdapter(options);
        projectMemberRecyclerView.setAdapter(memberListAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        memberListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        memberListAdapter.stopListening();
    }
}
