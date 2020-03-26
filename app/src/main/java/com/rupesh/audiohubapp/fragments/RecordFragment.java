package com.rupesh.audiohubapp.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.CurrentDate;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private EditText newFileText;
    private TextView recordMessage;
    private ImageButton recordBtn;
    boolean isRecording = false;
    private String newFileName;


    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    private MediaRecorder mediaRecorder;

    private Chronometer timer;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        rootView = inflater.inflate(R.layout.fragment_record, container, false);

        recordBtn = rootView.findViewById(R.id.record_btn);
        recordBtn.setOnClickListener(this);
        newFileText = rootView.findViewById(R.id.record_fragment_new_file);
        recordMessage = rootView.findViewById(R.id.recorded_file_name);
        timer = rootView.findViewById(R.id.record_timer);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (isRecording){
            // Stop recording
            stopRecording();
            recordBtn.setImageDrawable(getResources().getDrawable(R.drawable.record_btn_b, null));
            isRecording = false;
        } else {
            // Start recording
            if(checkPermissions()) {
                startRecording();
                recordBtn.setImageDrawable(getResources().getDrawable(R.drawable.record_btn_a, null));
                isRecording = true;
            }
        }
    }

    private void startRecording() {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

        // Get the current data and time for the file to be created
        // Set the filePath and filename
        CurrentDate currentDate = new CurrentDate();
        String dateTime = currentDate.getDate();
        String recordPath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        newFileName = newFileText.getText().toString() + ".3gp";
        recordMessage.setText("Recording File: " + newFileName);

        // Initialize mediaRecorder and set the source, outputFormat, outputFile and audioEncoder
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + newFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    private void stopRecording() {
        timer.stop();

        recordMessage.setText("Recording Finished, File Saved");


        mediaRecorder.stop();
        // Get the files calling .release and set to null so nxt time, if the btn is pressed
        // new MediaPlayer is initialized
        mediaRecorder.release();
        mediaRecorder = null;
    }


    // Ask for audio recording permission
    private boolean checkPermissions() {
        if(ActivityCompat.checkSelfPermission(getContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[] {recordPermission}, PERMISSION_CODE);
        }
        return false;
    }
}
