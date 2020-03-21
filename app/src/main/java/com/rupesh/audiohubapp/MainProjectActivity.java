package com.rupesh.audiohubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.model.Project;

public class MainProjectActivity extends AppCompatActivity {


    // Include toolbar in the mainActivity
    private Toolbar mToolbar;
    private Button mViewMembers;
    private RecyclerView projectRecyclerView;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_project);

        // Get the project from the Project Fragment
        //final String project_id = getIntent().getStringExtra("project_id");
        project = (Project) getIntent().getSerializableExtra("project");
        // Setup the tool bar
        mToolbar = findViewById(R.id.project_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Project");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewMembers = findViewById(R.id.main_project_view_members);
        projectRecyclerView = findViewById(R.id.recycleListMainProject);

        mViewMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent projectMemberIntent = new Intent(MainProjectActivity.this, ProjectMemberActivity.class);
                projectMemberIntent.putExtra("project", project);
                startActivity(projectMemberIntent);
            }
        });
    }

    // Setup menu, make it responsive when selected
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }


}
