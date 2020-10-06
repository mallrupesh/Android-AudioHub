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
import com.google.firebase.auth.FirebaseUser;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.presenter.MainActivityPresenter;

/**
 * MainActivity hosts two fragments ProjectFragment and NotificationFragment
 * Sets up handles UI menu items
 */
public class MainActivity extends AppCompatActivity {

    // Declare instance of firebase authorization
    //private FirebaseAuth mAuth;

    // Include toolbar in the mainActivity
    private Toolbar mToolbar;

    // Declare ViewPager
    private ViewPager mViewPager;

    // Declare Adapter
    private MainPagerSectionsAdapter mMainPagerSectionsAdapter;

    // Declare tab layout to set the view pager with tab layout
    private TabLayout mTabLayout;

    // Declare MainActivityPresenter
    private MainActivityPresenter mainActivityPresenter;

    // Action bar app title
    final static String APP_TITLE = "AudioHub";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init MainActivityPresenter
        mainActivityPresenter = new MainActivityPresenter();

        // Setup the tool bar
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(APP_TITLE);

        // Init viewPager tabs
        mViewPager = findViewById(R.id.main_tab_pager);

        // Init fragment adapter and set adapter to view pager
        mMainPagerSectionsAdapter = new MainPagerSectionsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainPagerSectionsAdapter);

        // Init tab layout and set it up with view pager
        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * Overridden method always executes on the start of the MainActivity to check if
     * a User is logged in. If logged in always start the app on MainActivity
     * otherwise start on StartActivity
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // Store it to FirebaseUser reference
        FirebaseUser currentUser = mainActivityPresenter.getCurrentAppUser();

        // If the user is null, navigate to StartActivity for Login or Registration
        if(currentUser == null){
            updateUI();
        }
    }

    /**
     * Prevents logged in User from navigating back to StartActivity (Login/ Register Activity)
     */
    private void updateUI() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();         // Do not want user to comeback when the back button is pressed
    }

    /**
     * Setup menu and make it responsive to user click
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Handles menu items which are settings and logout menu item
     * @param item
     * @return returns true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        // Sign out the user from the app
        if(item.getItemId() == R.id.main_logout){
            mainActivityPresenter.appSignOut();
            updateUI();
        }

        // Navigate to SettingsActivity on item click
        if(item.getItemId() == R.id.main_settings){
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return true;
    }
}




