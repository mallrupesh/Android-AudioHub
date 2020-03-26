/*
package com.rupesh.audiohubapp.dialogboxes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rupesh.audiohubapp.R;

public class RecordDialogBox extends DialogFragment {

    private EditText recordFile;
    private Button recordBtn;
    private Button cancelBtn;

    MediaRecorder mediaRecorder;
    private String newRecordFile;
    private String audioFilePath;
    private Boolean isRecording = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_record_file, null);

        builder.setView(view).setTitle("Recording Name");

        recordFile = view.findViewById(R.id.recording_file_name);
        recordBtn = view.findViewById(R.id.recording_record_btn);
        cancelBtn = view.findViewById(R.id.recording_cancel_btn);


        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NewAudio.mp3";


        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordAudio();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
                dismiss();
            }
        });


        return builder.create();
    }


    public void recordAudio(){
        isRecording = true;

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        }catch (Exception e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }


    public void stopAudio() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        //uploadAudio();
    }

    private void uploadAudio() {
    }
}
*/
