package com.rupesh.audiohubapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.MemberListAdapter;
import com.rupesh.audiohubapp.helper.MemberNetworkHelper;
import com.rupesh.audiohubapp.interfaces.InterfaceMemberCallback;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

// TODO List
//  1. list of user who are in the project
//  2. when pressed on the user then invite dialog box sud open
//  3. Current user sud be able to delete the user from the project
//  4. The arraylist of the member sud be passed to AllUserActivity as array

public class MembersFragment extends Fragment implements InterfaceMemberCallback {

    private View rootView;
    private Context context;
    private Button mAddMembersBtn;

    private RecyclerView projectMemberRecyclerView;
    private MemberListAdapter memberListAdapter;
    private Project project;
    private User user;

    private MemberNetworkHelper memberNetworkHelper;


    private DatabaseReference mProjectDatabaseRef;
    private DatabaseReference mUserDatabaseRef;

    private ArrayList<User> projectMembers;

    public MembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_members, container, false);

        //projectMemberRecyclerView = rootView.findViewById(R.id.recycleListViewProjectMembers);
       // mAddMembersBtn = rootView.findViewById(R.id.members_fragment_add_btn);

        // Get Project model object from MainProjectActivity through projectPagerSectionsAdapter
        project = (Project) getArguments().getSerializable("project");

        mProjectDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Projects");
        mUserDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        memberNetworkHelper = new MemberNetworkHelper(project);
        memberNetworkHelper.interfaceMemberCallback = this;

        memberNetworkHelper.getMember();

        //initUI();

       /* mAddMembersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allUsersActivity = new Intent(rootView.getContext(), AllUsersActivity.class);
                allUsersActivity.putExtra("project", project);
                startActivity(allUsersActivity);
            }
        });*/

        return rootView;
    }

    @Override
    public void mapUser(ArrayList<User> proMembers) {

        projectMembers = proMembers;
       // Log.d("USERS_", "Users-> " + projectMembers);

        initUI();

    }

    private void initUI() {
        projectMemberRecyclerView = rootView.findViewById(R.id.recycleListViewProjectMembers);
        memberListAdapter = new MemberListAdapter(projectMembers, getContext());
        projectMemberRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        projectMemberRecyclerView.setAdapter(memberListAdapter);
    }
}










/*
    project.members = ["uid1", "uid2", "uid3"];
    forloop
        go to user db, ask uid1,
        go to user db, ask uid2
        go to user db, ask uid3
    variable list add user1, user2,user3
    usersarray
  show it to recycleview
 */

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