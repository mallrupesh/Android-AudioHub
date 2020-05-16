package com.rupesh.audiohubapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.MemberListAdapter;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.presenter.MembersFragPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class MembersFragment extends Fragment {

    private View rootView;
    private RecyclerView projectMemberRecyclerView;
    private MemberListAdapter memberListAdapter;
    private Project project;

    private MembersFragPresenter membersFragPresenter;

    public MembersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_members, container, false);

        // Get Project model object from MainProjectActivity through projectPagerSectionsAdapter
        project = (Project) getArguments().getSerializable("project");

        membersFragPresenter = new MembersFragPresenter(this);
        membersFragPresenter.displayMembers();

        return rootView;
    }

    public void initUI(ArrayList<User> projectMembers) {
        projectMemberRecyclerView = rootView.findViewById(R.id.recycleListViewProjectMembers);
        memberListAdapter = new MemberListAdapter(projectMembers, getContext());
        projectMemberRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        projectMemberRecyclerView.setAdapter(memberListAdapter);
    }

    public Project getProject() {
        return project;
    }
}
