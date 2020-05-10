package com.rupesh.audiohubapp.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rupesh.audiohubapp.fragments.MyFilesFragment;
import com.rupesh.audiohubapp.fragments.ProjectsFragment;

class MainPagerSectionsAdapter extends FragmentStatePagerAdapter {

    public MainPagerSectionsAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    // Returns the position of each tab
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new MyFilesFragment();

            case 1: return new ProjectsFragment();

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;   // We have 2 fragments in the MainActivity
    }


    // Returns the tab title
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "MYFILES";

            case 1: return "PROJECTS";

            default: return null;
        }
    }
}
