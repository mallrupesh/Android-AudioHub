package com.rupesh.audiohubapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.MyFilesListAdapter;
import com.rupesh.audiohubapp.model.File;
import com.rupesh.audiohubapp.model.Project;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilesFragment extends Fragment {

    private View rootView;
    private RecyclerView filesRecyclerView;
    private MyFilesListAdapter filesListAdapter;

    private ConstraintLayout audioPlayerSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    private DatabaseReference projectFilesDataRef;

    private Project project;

    public FilesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_files, container, false);
        audioPlayerSheet = rootView.findViewById(R.id.audio_player_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(audioPlayerSheet);

        // Get Project model object from MainProjectActivity through projectPagerSectionsAdapter
        project = (Project) getArguments().getSerializable("project");

        projectFilesDataRef = FirebaseDatabase.getInstance().getReference().child("Project_Files");

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // No need to do anything
            }
        });

        //initUI();

        return rootView;
    }


    private void initUI() {
        filesRecyclerView = rootView.findViewById(R.id.recycleListViewFiles);
        filesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<File> options =
                new FirebaseRecyclerOptions.Builder<File>()
                        .setQuery(projectFilesDataRef.child(project.getProjectId()), File.class).build();
        filesListAdapter = new MyFilesListAdapter(options);
        filesRecyclerView.setAdapter(filesListAdapter);
    }

   /* @Override
    public void onStart() {
        super.onStart();
        filesListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        filesListAdapter.stopListening();
    }*/
}
