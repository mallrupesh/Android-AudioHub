package com.rupesh.audiohubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

public class ProjectMemberActivity extends AppCompatActivity implements InterfaceInvite {

    // Include toolbar in the mainActivity
    private Toolbar mToolbar;
    private Button mAddMembers;
    private RecyclerView projectMemberRecyclerView;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_member);

        mToolbar = findViewById(R.id.project_member_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Members");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* // Get the project id from the MainProjectActivity
        final String project_id = getIntent().getStringExtra("project_id");
*/
        project = (Project) getIntent().getSerializableExtra("project");

        mAddMembers = findViewById(R.id.project_members_add_btn);
        projectMemberRecyclerView = findViewById(R.id.recycleListProjectMembers);

        mAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allUsersActivity = new Intent(ProjectMemberActivity.this, AllUsersActivity.class);
                allUsersActivity.putExtra("projectActivity",true);
                startActivity(allUsersActivity);
            }
        });

    }

    @Override
    public void inviteMember(User user) {
        // project, user
    }
}
