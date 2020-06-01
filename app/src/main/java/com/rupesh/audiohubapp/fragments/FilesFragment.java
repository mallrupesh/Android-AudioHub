package com.rupesh.audiohubapp.fragments;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.activities.PlayerActivity;
import com.rupesh.audiohubapp.adapters.FilesListAdapter;
import com.rupesh.audiohubapp.model.File;
import com.rupesh.audiohubapp.model.Project;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilesFragment extends Fragment implements FilesListAdapter.OnItemClickListener {

    private View rootView;
    private RecyclerView filesRecyclerView;
    private FilesListAdapter filesListAdapter;

   // private ConstraintLayout audioPlayerSheet;
    //private BottomSheetBehavior bottomSheetBehavior;

    private DatabaseReference projectFilesDataRef;

    private Project project;

    private String filePath;

    // For MediaPlayer
    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;
    private File fileToPlay;

    public FilesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_files, container, false);
       // audioPlayerSheet = rootView.findViewById(R.id.audio_player_sheet);
       // bottomSheetBehavior = BottomSheetBehavior.from(audioPlayerSheet);

        // Get Project model object from MainProjectActivity through projectPagerSectionsAdapter
        project = (Project) getArguments().getSerializable("project");

        projectFilesDataRef = FirebaseDatabase.getInstance().getReference().child("Project_Files");

       /* bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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
        });*/

        initUI();

        return rootView;
    }


    private void initUI() {
        filesRecyclerView = rootView.findViewById(R.id.recycleListViewFiles);
        filesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<File> options =
                new FirebaseRecyclerOptions.Builder<File>()
                        .setQuery(projectFilesDataRef.child(project.getProjectId()), File.class).build();
        filesListAdapter = new FilesListAdapter(options, this);
        filesRecyclerView.setAdapter(filesListAdapter);
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


        /*projectFilesDataRef.child(project.getProjectId()).addListenerForSingleValueEvent(new ValueEventListener() {
            String fileId;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    fileId = child.getKey();
                    //Log.d("fileId", fileId);
                }

                projectFilesDataRef.child(project.getProjectId()).child(fileId).addListenerForSingleValueEvent(new ValueEventListener() {

                    String fileUrl;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        fileUrl = dataSnapshot.child("fileUrl").getValue().toString();
                        Log.d("fileId", fileUrl);
                        playAudio(fileUrl);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });*/



       /* if(isPlaying) {

            stopAudio();
            playAudio(fileToPlay);
            isPlaying = true;
        }else {

            fileToPlay = file;
            playAudio(fileToPlay);
            isPlaying = true;
        }*/

        //Log.d("PLAY_LOG", "Playing" + file.getName());
        //Log.d("PLAY_LOG", "Message " + file.getFileUrl());
    }






    private void playAudio(String fileUrl) {

        mediaPlayer = new MediaPlayer();
       // mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            //mediaPlayer.setDataSource(filePath = getActivity().getExternalFilesDir("/").getAbsolutePath() + "/testtim.mp3");
            //mediaPlayer.setDataSource(localFilePath = );
            mediaPlayer.setDataSource(fileUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(getContext(), "Playing audio", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }

        isPlaying = true;
    }

    private void stopAudio() {
        isPlaying = false;
    }

    public void getAudioFile() {
        String fileId = projectFilesDataRef.child(project.getProjectId()).push().getKey();

        Log.d("ProjectId", project.getProjectId());
        Log.d("fileId", fileId);
    }

}
