package com.rupesh.audiohubapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rupesh.audiohubapp.R;

public class MainActivity extends AppCompatActivity {

    // Declare instance of firebase authorization
    private FirebaseAuth mAuth;

    // Include toolbar in the mainActivity
    private Toolbar mToolbar;

    // Declare ViewPager
    private ViewPager mViewPager;

    // Declare Adapter
    private MainPagerSectionsAdapter mMainPagerSectionsAdapter;

    // Declare tab layout to set the view pager with tab layout
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Setup the tool bar
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("AudioHub");

        // Init viewPager tabs
        mViewPager = findViewById(R.id.main_tab_pager);

        // Init fragment adapter and set adapter to view pager
        mMainPagerSectionsAdapter = new MainPagerSectionsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainPagerSectionsAdapter);

        // Init tab layout and set it up with view pager
        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // Store it to FirebaseUser reference
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // If the user is null, navigate to StartActivity for Login or Registration
        if(currentUser == null){

            updateUI();
        }
    }


    private void updateUI() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();                   // Do not want user to comeback when the back button is pressed
    }



    // Setup menu, make it responsive when selected
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    // When the menu item is selected, the app should sign out the user
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout){
            FirebaseAuth.getInstance().signOut();
            updateUI();
        }

        if(item.getItemId() == R.id.main_settings){
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }


        if(item.getItemId() == R.id.main_all_users){
            Intent settingsIntent = new Intent(MainActivity.this, AllUsersActivity.class);
            startActivity(settingsIntent);
        }

        return true;
    }

}




