package com.rupesh.audiohubapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.ProjectListAdapter;
import com.rupesh.audiohubapp.presenter.ProjectsFragPresenter;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * Initialises UI components and displays project list
 */
public class ProjectsFragment extends Fragment {

    private View rootView;
    private EditText mProjectName;
    private Button mProjectBtn;
    private RecyclerView recyclerView;

    private ProjectsFragPresenter projectsFragPresenter;
    private ProjectListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_project, container, false);

        // Init View components
        mProjectName = rootView.findViewById(R.id.projects_fragment_newProjectTextView);
        mProjectBtn = rootView.findViewById(R.id.projects_fragment_btnAdd);

        projectsFragPresenter = new ProjectsFragPresenter();
        addProject();
        initUI();

        return rootView;
    }

    /**
     *  Add Project on buttonClicked
     */
    private void addProject(){
        mProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectsFragPresenter.createProject(mProjectName.getText().toString());
                // Clear project input text
                mProjectName.getText().clear();
                Toast.makeText(getContext(), "Project created successfully", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void removeProject(String projectTitle) {
        projectsFragPresenter.deleteProject(projectTitle);
    }

    /**
     * Init and display project list
     *
     */
    private void initUI() {
        recyclerView = rootView.findViewById(R.id.recycleListViewProject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProjectListAdapter(projectsFragPresenter.queryData(), this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
