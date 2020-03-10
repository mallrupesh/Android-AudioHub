package com.rupesh.audiohubapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerSectionsAdapter extends FragmentPagerAdapter {

    public PagerSectionsAdapter(FragmentManager fm) {
        super(fm);
    }


    // Returns the position of each tab
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MyFilesFragment myFilesFragment = new MyFilesFragment();
                return myFilesFragment;

            case 1:
                ProjectsFragment projectsFragment = new ProjectsFragment();
                return projectsFragment;

            case 2:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;   // We have 3 tabs
    }


    // Returns the tab title
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "MYFILES";

            case 1:
                return "PROJECTS";

            case 2:
                return "PROFILE";

            default:
                return null;
        }
    }
}
