package com.rupesh.audiohubapp;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.model.Members;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends Fragment {

    View rootView;
    Context context;
    private RecyclerView recyclerView;
    ProjectListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_project, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleListViewProject);

        initUI();
        return rootView;

    }

    private void initUI(){
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleListViewProject);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        FirebaseRecyclerOptions<Members> options =
                new FirebaseRecyclerOptions.Builder<Members>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Members"), Members.class)
                        .build();

        adapter = new ProjectListAdapter(options);
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
