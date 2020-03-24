package com.rupesh.audiohubapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.activities.AllUsersActivity;
import com.rupesh.audiohubapp.model.Project;

/**
 * A simple {@link Fragment} subclass.
 */

// TODO List
//  1. list of user who are in the project
//  2. when pressed on the user then invite dialog box sud open
//  3. Current user sud be able to delete the user from the project
//  4. The arraylist of the member sud be passed to AllUserActivity as array
public class MembersFragment extends Fragment {

    View rootView;
    Context context;
    private Button mAddMembersBtn;
    private RecyclerView projectMemberRecyclerView;
    private Project project;


    DatabaseReference inviteDatabaseRef;


    FirebaseUser mCurrentUser;


    public MembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_members, container, false);

        projectMemberRecyclerView = rootView.findViewById(R.id.recycleListProjectMembers);
        mAddMembersBtn = rootView.findViewById(R.id.members_fragment_add_btn);

        // Get Project model object from MainProjectActivity through projectPagerSectionsAdapter
        project = (Project) getArguments().getSerializable("project");

        // Setup OnClickListener to ADDMEMBER btn and send projectID upon the navigation to
        // AllUserActivity with the request code 2
        mAddMembersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allUsersActivity = new Intent(rootView.getContext(), AllUsersActivity.class);
                allUsersActivity.putExtra("project", project);
                startActivity(allUsersActivity);
                //startActivityForResult(allUsersActivity, 2);
            }
        });

        getMembers();

        return rootView;

    }

    public void getMembers() {

        // project.getMembers() -> ["","",""] // for loop
        // this members calls all members users and display it to recycleview
    }

 /*   @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 ) {
            int state = data.getIntExtra("state", 0);
            User user = (User) data.getSerializableExtra("user");
            this.handleUserInput(user,state);
        }
    }

    public void handleUserInput(User user, int state) {

        switch (state) {
            case 0:
                System.out.println("Not in project");
                sendRequest(user);
                break;
            case 1:
                System.out.println("Request sent");
                cancelRequest(user);
                break;
            case 2:
                System.out.println("Request received");
                acceptRequest(user);
                break;
            case 3:
                System.out.println("In project");
                leaveProject(user);
                break;
            default:
                break;
        }
    }*/

    /*public void sendRequest(User user) {

        inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).child("request_type").setValue("sent");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").setValue("received");
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("project_type").setValue(project.getProjectId());
    }


    public void cancelRequest(User user) {
        inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).removeValue();
        inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").removeValue();
    }

    public void acceptRequest(final User user) {
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user.getUid())){
                   String currentProject = dataSnapshot.child(user.getUid()).child("project_type").getValue().toString();

                    projectDatabaseRef.child(currentProject).child("members").push().setValue(mCurrentUser.getUid());
                    inviteDatabaseRef.child(mCurrentUser.getUid()).child(user.getUid()).removeValue();
                    inviteDatabaseRef.child(user.getUid()).child(mCurrentUser.getUid()).child("request_type").removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void leaveProject(User user) {
        projectDatabaseRef.child("Projects").child("member").removeValue();
    }*/
}
