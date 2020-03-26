package com.rupesh.audiohubapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.rupesh.audiohubapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilesFragment extends Fragment {

    private View rootView;
    private RecyclerView filesRecyclerView;
    private ConstraintLayout audioPlayerSheet;
    private BottomSheetBehavior bottomSheetBehavior;

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


        return rootView;
    }
}
