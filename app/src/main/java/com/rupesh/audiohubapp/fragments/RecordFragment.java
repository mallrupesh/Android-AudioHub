package com.rupesh.audiohubapp.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.presenter.RecordFragPresenter;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * Initialises Audio record UI components and handles recording operations
 */
public class RecordFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private EditText newFileText;
    private TextView recordMessage;
    private ImageButton recordBtn;
    private boolean isRecording = false;
    private String newFileName;


    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    private MediaRecorder mediaRecorder;
    private Chronometer timer;
    private String localFilePath;

    private ProgressDialog progressDialog;
    private Project project;

    private RecordFragPresenter recordFragPresenter;

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
        progressDialog = new ProgressDialog(getContext());

        // Get Project model object from MainProjectActivity through projectPagerSectionsAdapter
        project = (Project) getArguments().getSerializable("project");

        recordFragPresenter = new RecordFragPresenter(this);

        //audioStorageRef = FirebaseStorage.getInstance().getReference();
        //projectFilesDataRef = FirebaseDatabase.getInstance().getReference().child("Project_Files");

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

        localFilePath = getActivity().getExternalFilesDir("/Recordings/").getAbsolutePath();
        Log.d("LPath", localFilePath);

        newFileName = newFileText.getText().toString() + ".m4a";
        //Log.d("FName", newFileName );

        recordMessage.setText("Recording " + newFileName + "...");

        // Initialize mediaRecorder and set the source, outputFormat, outputFile and audioEncoder
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioEncodingBitRate(16*44100);
        mediaRecorder.setAudioSamplingRate(44100);
        mediaRecorder.setOutputFile(localFilePath + "/" + newFileName);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        newFileText.getText().clear();
        mediaRecorder.start();
    }

    private void stopRecording() {
        timer.stop();

        recordMessage.setText("Recording Completed");


        mediaRecorder.stop();
        // Get the files calling .release and set to null so nxt time, if the btn is pressed
        // new MediaPlayer is initialized
        mediaRecorder.release();
        mediaRecorder = null;

        uploadAudio();
    }

    private void uploadAudio() {
        progressDialog.setMessage("Uploading file to Cloud");
        progressDialog.show();
        recordFragPresenter.uploadFile();
    }

    public String getNewFileName() {
        return newFileName;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public Project getProject() {
        return project;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
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
