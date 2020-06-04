package com.rupesh.audiohubapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.activities.PlayerActivity;
import com.rupesh.audiohubapp.adapters.FilesListAdapter;
import com.rupesh.audiohubapp.model.File;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.presenter.FilesFragPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilesFragment extends Fragment implements FilesListAdapter.OnItemClickListener {

    private View rootView;
    private RecyclerView filesRecyclerView;
    private FilesListAdapter filesListAdapter;

    private Project project;

    private FilesFragPresenter filesFragPresenter;


    public FilesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_files, container, false);
        project = (Project) getArguments().getSerializable("project");
        filesFragPresenter = new FilesFragPresenter(this);
        initUI();
        return rootView;
    }

    private void initUI() {
        filesRecyclerView = rootView.findViewById(R.id.recycleListViewFiles);
        filesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filesListAdapter = new FilesListAdapter(filesFragPresenter.queryData(), this, this);
        filesRecyclerView.setAdapter(filesListAdapter);
    }

    public void removeFile(String name) {
        filesFragPresenter.deleteFile(name);
    }

    @Override
    public void onStart() {
        super.onStart();
        filesListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        filesListAdapter.stopListening();
    }

    @Override
    public void onItemClicked(File file) {
        Intent intent = new Intent(getContext(), PlayerActivity.class);
        intent.putExtra("file", file);
        intent.putExtra("project", project);
        startActivity(intent);
    }

    public Project getProject() {
        return project;
    }
}
