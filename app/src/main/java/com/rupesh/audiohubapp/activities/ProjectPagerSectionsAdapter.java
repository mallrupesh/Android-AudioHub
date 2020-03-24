package com.rupesh.audiohubapp.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rupesh.audiohubapp.fragments.FilesFragment;
import com.rupesh.audiohubapp.fragments.MembersFragment;
import com.rupesh.audiohubapp.model.Project;

public class ProjectPagerSectionsAdapter extends FragmentPagerAdapter {

    Project project;

    public ProjectPagerSectionsAdapter(@NonNull FragmentManager fm, Project project) {
        super(fm);
        this.project = project;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FilesFragment();

            case 1:
                // Create the bundle to pass to pass the Project model object
                // to the MembersFragment
                Bundle bundle = new Bundle();
                MembersFragment membersFragment = new MembersFragment();
                bundle.putSerializable("project", project);
                membersFragment.setArguments(bundle);
                return membersFragment;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;             // We have two fragments in the MainProjectActivity
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
