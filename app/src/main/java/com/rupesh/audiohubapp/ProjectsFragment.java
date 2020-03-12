package com.rupesh.audiohubapp;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends Fragment {

    View rootView;
    Context context;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_project, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleListViewProject);


        return rootView;

    }

    private void initUI(){
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleListViewProject);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
