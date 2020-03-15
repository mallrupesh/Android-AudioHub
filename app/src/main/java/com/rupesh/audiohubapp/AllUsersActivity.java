package com.rupesh.audiohubapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.view.adapters.AllUserListAdapter;

public class AllUsersActivity extends AppCompatActivity {

    // Include toolbar in the mainActivity
    private Toolbar mToolbar;
    private RecyclerView allUsersRecyclerView;

    AllUserListAdapter allUserListAdapter;

    private DatabaseReference mUserDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        mToolbar = findViewById(R.id.all_users_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        allUsersRecyclerView = findViewById(R.id.recycleListAllUsers);

        initUI();

    }

    private void initUI(){
        allUsersRecyclerView = findViewById(R.id.recycleListAllUsers);
        allUsersRecyclerView.setLayoutManager(new LinearLayoutManager(AllUsersActivity.this));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mUserDataRef, User.class)
                        .build();
        allUserListAdapter = new AllUserListAdapter(options);
        allUsersRecyclerView.setAdapter(allUserListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        allUserListAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        allUserListAdapter.stopListening();
    }
}
