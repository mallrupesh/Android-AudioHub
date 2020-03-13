package com.rupesh.audiohubapp.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProjectPagerSectionsAdapter extends FragmentPagerAdapter {
    public ProjectPagerSectionsAdapter(FragmentManager fm) {
        super(fm);
    }


    // Returns the position of each tab
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FilesFragment();

            case 1: return new MembersFragment();

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;   // We have 2 tabs
    }


    // Returns the tab title
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "FILES";

            case 1: return "MEMBERS";

            default: return null;
        }
    }
}
