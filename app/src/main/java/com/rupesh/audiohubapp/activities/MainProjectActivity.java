package com.rupesh.audiohubapp.activities;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.ProjectPagerSectionsAdapter;
import com.rupesh.audiohubapp.model.Project;

/**
 * MainProjectActivity hosts four fragments
 * RecordFragment
 * FilesFragment
 * MembersFragment
 * SearchFragment
 *
 * Connects those fragments using the ViewPagerAdapter
 */
public class MainProjectActivity extends AppCompatActivity {

    // Include toolbar in the mainActivity
    private Toolbar mToolbar;
    private Project project;

    // Declare ViewPager and ViewPagerAdapter
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
        setUpToolbar();

        // Find Viewpager resource
        mViewPager = findViewById(R.id.project_tab_pager);

        // Pass the Project model object received from ProjectListAdapter to the projectPagerSectionsAdapter
        mProjectPagerSectionsAdapter = new ProjectPagerSectionsAdapter(getSupportFragmentManager(), project);
        mViewPager.setAdapter(mProjectPagerSectionsAdapter);

        // Init tab layout and set it up with view pager
        mTabLayout = findViewById(R.id.project_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Set up Activity tool bar with the current Project name
     */
    public void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(project.getProjectName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
