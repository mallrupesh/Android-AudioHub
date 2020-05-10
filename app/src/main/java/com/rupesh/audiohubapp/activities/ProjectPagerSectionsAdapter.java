package com.rupesh.audiohubapp.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rupesh.audiohubapp.fragments.FilesFragment;
import com.rupesh.audiohubapp.fragments.MembersFragment;
import com.rupesh.audiohubapp.fragments.RecordFragment;
import com.rupesh.audiohubapp.model.Project;

public class ProjectPagerSectionsAdapter extends FragmentPagerAdapter {

    private Project project;

    public ProjectPagerSectionsAdapter(@NonNull FragmentManager fm, Project project) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.project = project;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                // Create the bundle to pass to pass the Project model object
                // to the MembersFragment
                Bundle bundleToRecFrag = new Bundle();
                RecordFragment recordFragment = new RecordFragment();
                bundleToRecFrag.putSerializable("project", project);
                recordFragment.setArguments(bundleToRecFrag);
                return recordFragment;

            case 1:
                // Create the bundle to pass to pass the Project model object
                // to the MembersFragment
                Bundle bundleToFileFrag = new Bundle();
                FilesFragment filesFragment = new FilesFragment();
                bundleToFileFrag.putSerializable("project", project);
                filesFragment.setArguments(bundleToFileFrag);
                return filesFragment;

            case 2:
                // Create the bundle to pass to pass the Project model object
                // to the MembersFragment
                Bundle bundleToMemFrag = new Bundle();
                MembersFragment membersFragment = new MembersFragment();
                bundleToMemFrag.putSerializable("project", project);
                membersFragment.setArguments(bundleToMemFrag);
                return membersFragment;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;             // We have two fragments in the MainProjectActivity
    }

    // Returns the tab title
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "RECORD";

            case 1: return "FILES";

            case 2: return "MEMBERS";

            default: return null;
        }
    }
}
