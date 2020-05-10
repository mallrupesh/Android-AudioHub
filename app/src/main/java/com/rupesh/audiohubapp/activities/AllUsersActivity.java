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
import com.rupesh.audiohubapp.dialogboxes.InviteDialogBox;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.adapters.AllUserListAdapter;

public class AllUsersActivity extends AppCompatActivity implements AllUserListAdapter.OnItemClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        mToolbar = findViewById(R.id.all_users_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        project = (Project) getIntent().getSerializableExtra("project");

        allUsersRecyclerView = findViewById(R.id.recycleListViewAllUser);

        // As this activity implements the AllUserAdapter nested interface
        listener = AllUsersActivity.this;

        initUI();
    }

    private void initUI(){
        allUsersRecyclerView = findViewById(R.id.recycleListViewAllUser);
        allUsersRecyclerView.setLayoutManager(new LinearLayoutManager(AllUsersActivity.this));
        //listener = AllUsersActivity.this;

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
}


