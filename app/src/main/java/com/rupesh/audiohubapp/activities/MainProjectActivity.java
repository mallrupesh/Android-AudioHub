package com.rupesh.audiohubapp.activities;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.Project;

public class MainProjectActivity extends AppCompatActivity {


    // Include toolbar in the mainActivity
    private Toolbar mToolbar;
    private Button mViewMembers;
    private RecyclerView projectRecyclerView;
    private Project project;

    // Declare ViewPager
    private ViewPager mViewPager;

    private ProjectPagerSectionsAdapter mProjectPagerSectionsAdapter;

    // Declare tab layout to set the view pager with tab layout
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_project);


        // Get the Project model object from the ProjectLisAdapter which is connected to the Project Fragment
        project = (Project) getIntent().getSerializableExtra("project");


        // Setup the tool bar
        mToolbar = findViewById(R.id.project_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Project");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = findViewById(R.id.project_tab_pager);

        // Instantiate ProjectPagerSectionAdopter that connects the MainProjectActivity with FilesFragment and MembersFragment
        // Pass the Project model object received from ProjectListAdapter to the projectPagerSectionsAdapter to be sent to Members Fragment
        mProjectPagerSectionsAdapter = new ProjectPagerSectionsAdapter(getSupportFragmentManager(), project);
        mViewPager.setAdapter(mProjectPagerSectionsAdapter);

        // Init tab layout and set it up with view pager
        mTabLayout = findViewById(R.id.project_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    // Setup menu, make it responsive when selected
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }
}
