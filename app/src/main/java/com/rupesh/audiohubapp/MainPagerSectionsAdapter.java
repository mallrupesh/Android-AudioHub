package com.rupesh.audiohubapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rupesh.audiohubapp.fragments.MyFilesFragment;
import com.rupesh.audiohubapp.fragments.ProjectsFragment;

class MainPagerSectionsAdapter extends FragmentPagerAdapter {

    public MainPagerSectionsAdapter(FragmentManager fm) {
        super(fm);
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
        return 2;   // We have 3 tabs
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
