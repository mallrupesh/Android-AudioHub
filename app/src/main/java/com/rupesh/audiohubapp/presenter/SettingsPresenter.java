package com.rupesh.audiohubapp.presenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rupesh.audiohubapp.activities.SettingsActivity;
import com.theartofdev.edmodo.cropper.CropImage;

public class SettingsPresenter {

    private SettingsActivity settingsActivity;

    private DatabaseReference mUserRef;
    private FirebaseUser mCurrentUser;

    //Firebase Storage reference
    private StorageReference mImageStorage;

    // For accessing the phone gallery
    private static final int GALLERY_PICK = 1;
    private String storagePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private int PERMISSION_CODE = 21;

    public SettingsPresenter(SettingsActivity settingsActivity) {

        this.settingsActivity = settingsActivity;
        mImageStorage = FirebaseStorage.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Set reference to Users object "node" in Firebase Database
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
    }

    public void setUserImg() {
        // Retrieve User Data from the Firebase in the Settings Activity
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                settingsActivity.initUI(name, status, image, email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void uploadImage(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_PICK && resultCode == settingsActivity.RESULT_OK ) {

            Uri imageUri = data.getData();

            // Crop image in square resolution and start Settings Activity
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(settingsActivity);
        }

        // Check that if result is from Crop Activity and getUri of that Crop Activity result
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == settingsActivity.RESULT_OK) {

                settingsActivity.setupProgressDialog();

                Uri resultUri = result.getUri();

                // Get current UID of the user and store  it in a String
                final String currentUserId = mCurrentUser.getUid();

                // As we have already init the StorageReference above, just need to create
                // filePath with the above created Storage reference
                // Then, first store image name with current user id in Firebase Storage
                // Second, store the image link in Firebase Database
                final StorageReference imageFilePath = mImageStorage.child("profile_images").child(currentUserId+".jpg");
                //final StorageReference imageFilePath = mImageStorage.child("profile_images");

                imageFilePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUrl = uri.toString();
                                mUserRef.child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            settingsActivity.onSuccessImgUpload("Profile image changed");
                                            //mProgressDialog.dismiss();
                                            //Toast.makeText(settingsActivity,"Profile img changed", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            settingsActivity.onErrorImgUpload("Error loading image");
                                            //mProgressDialog.dismiss();
                                            //Toast.makeText(settingsActivity,"Error loading img", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    // Ask for audio recording permission
    public boolean checkPermissions() {
        if(ActivityCompat.checkSelfPermission(settingsActivity, storagePermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            ActivityCompat.requestPermissions(settingsActivity, new String[] {storagePermission}, PERMISSION_CODE);
        }
        return false;
    }
}
