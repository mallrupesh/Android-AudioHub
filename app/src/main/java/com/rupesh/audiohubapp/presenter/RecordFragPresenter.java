package com.rupesh.audiohubapp.presenter;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rupesh.audiohubapp.fragments.RecordFragment;
import com.rupesh.audiohubapp.model.CurrentDate;

import java.io.File;
import java.util.HashMap;

/**
 * Performs Firebase operations
 */
public class RecordFragPresenter {

    private static final String PROJECT_FILES = "Project_Files";
    private StorageReference audioStorageRef;
    private DatabaseReference projectFilesDataRef;
    private RecordFragment recordFragment;

    public RecordFragPresenter(RecordFragment recordFragment) {
        this.recordFragment = recordFragment;
        audioStorageRef = FirebaseStorage.getInstance().getReference();
        projectFilesDataRef = FirebaseDatabase.getInstance().getReference().child(PROJECT_FILES);
    }

    /**
     * Upload audio file
     */
    public void uploadFile() {
        final StorageReference uploadFilePath = audioStorageRef.child("audio_files").child(recordFragment.getNewFileName());
        Uri uri = Uri.fromFile(new File(recordFragment.getLocalFilePath() + "/" + recordFragment.getNewFileName()));

        uploadFilePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                recordFragment.getProgressDialog().dismiss();
                uploadFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUrl = uri.toString();
                        CurrentDate currentDate = new CurrentDate();
                        String fileUid = projectFilesDataRef.push().getKey();

                        HashMap<String, String> projectFileMap = new HashMap<>();
                        projectFileMap.put("name", recordFragment.getNewFileName());
                        projectFileMap.put("createdOn", currentDate.getDate());
                        projectFileMap.put("fileUrl", downloadUrl);
                        projectFileMap.put("fileId", fileUid);
                        projectFilesDataRef.child(recordFragment. getProject().getProjectId()).child(fileUid).setValue(projectFileMap);
                    }
                });
            }
        });
    }
}
