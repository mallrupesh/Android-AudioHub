package com.rupesh.audiohubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectMemberActivity extends AppCompatActivity {

    // Include toolbar in the mainActivity
    private Toolbar mToolbar;
    private Button mAddMembers;
    private RecyclerView projectMemberRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_member);

        mToolbar = findViewById(R.id.project_member_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Members");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddMembers = findViewById(R.id.project_members_add_btn);
        projectMemberRecyclerView = findViewById(R.id.recycleListProjectMembers);

        mAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allUsersActivity = new Intent(ProjectMemberActivity.this, AllUsersActivity.class);
                startActivity(allUsersActivity);
            }
        });

    }
}
