package com.rupesh.audiohubapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rupesh.audiohubapp.fragments.ProjectPagerSectionsAdapter;

public class MainProjectActivity extends AppCompatActivity {


    // Include toolbar in the mainActivity
    private Toolbar mToolbar;

    // Declare ViewPager
    private ViewPager mViewPager;

    // Declare Adapter
    private ProjectPagerSectionsAdapter projectPagerSectionsAdapter;

    // Declare tab layout to set the view pager with tab layout
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_project);

        // Setup the tool bar
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Project");

        // Init viewPager tabs
        mViewPager = findViewById(R.id.main_tab_pager);

        // Init fragment adapter and set adapter to view pager
        projectPagerSectionsAdapter = new ProjectPagerSectionsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(projectPagerSectionsAdapter);

        // Init tab layout and set it up with view pager
        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
