package com.rupesh.audiohubapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.AllUserListAdapter;
import com.rupesh.audiohubapp.adapters.NotificationListAdapter;
import com.rupesh.audiohubapp.dialogboxes.InviteDialogBox;
import com.rupesh.audiohubapp.helper.RequestHelper;
import com.rupesh.audiohubapp.interfaces.InterfaceRequestCallBack;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

public class AllUsersActivity extends AppCompatActivity implements AllUserListAdapter.OnItemClickListener, InterfaceRequestCallBack {

    // Include toolbar in the mainActivity
    private Toolbar mToolbar;

    // Declare RecycleView
    private RecyclerView allUsersRecyclerView;

    // Declare adapter to be used with RecyclerView
    AllUserListAdapter allUserListAdapter;

    // Declare Firebase Database reference
    private DatabaseReference mUserDataRef;


    // Declare context and listener to be used by the RecyclerView adapter
    private Context context;

    private AllUserListAdapter.OnItemClickListener listener;

    // Declare String to track Project id
    //private String projectId;

    // Member -> AllUser (NotNull) Main -> AllUser (null)
    private Project project;




    private ArrayList<User> requestUsers;

    private NotificationListAdapter notificationListAdapter;

    private RecyclerView notificationRecyclerView;

    private RequestHelper requestHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification);


        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        project = (Project) getIntent().getSerializableExtra("project");

        //Log.d("USERS_", "Users " + project);

        allUsersRecyclerView = findViewById(R.id.recycleListViewAllUsers);

        // As this activity implements the AllUserAdapter nested interface
        listener = AllUsersActivity.this;

        requestHelper = new RequestHelper();
        requestHelper.interfaceRequestCallBack = this;

        requestHelper.searchUser();

        initUI();
    }

    private void initUI(){
        allUsersRecyclerView = findViewById(R.id.recycleListViewAllUsers);
        allUsersRecyclerView.setLayoutManager(new LinearLayoutManager(AllUsersActivity.this));

        // TODO list
        // 1. filter all user who are already in the project.
        // 2. list sud contain all the user who are not in the project.
        // 3. we need a list of users from the MembersFragment to filter them out from the search.

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mUserDataRef, User.class)
                        .build();
        allUserListAdapter = new AllUserListAdapter(options, listener, context);
        allUsersRecyclerView.setAdapter(allUserListAdapter);
    }

    // On Activity start, set adapter to start listening
    @Override
    protected void onStart() {
        super.onStart();
        allUserListAdapter.startListening();
    }


    // On Activity stop, set adapter to stop listening
    @Override
    protected void onStop() {
        super.onStop();
        allUserListAdapter.stopListening();
    }


    // Implementing nested interface in AllUserListAdapter to get the User data
    // and pass to the InviteDialogBox Activity
    // Additionally display the dialog box in recycler view item clicked
    @Override
    public void onItemClicked(View v, User user) {
        InviteDialogBox inviteDialogBox = new InviteDialogBox();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        // can be null when Main -> AllUser
        bundle.putSerializable("project", project);
        inviteDialogBox.setArguments(bundle);

        //inviteDialogBox.inviteInterface = this;
        inviteDialogBox.show(getSupportFragmentManager(),"inviteDialog");
    }

    @Override
    public void mapRequest(ArrayList<User> users) {
        requestUsers = users;
        initUINotification();
    }

    public void initUINotification() {
        notificationRecyclerView = findViewById(R.id.recycleListViewNotification);
        notificationListAdapter = new NotificationListAdapter(requestUsers,context);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        notificationRecyclerView.setAdapter(notificationListAdapter);
    }
}


