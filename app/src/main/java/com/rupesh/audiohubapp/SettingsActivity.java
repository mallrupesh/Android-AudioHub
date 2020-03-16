package com.rupesh.audiohubapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView mDisplayImage;
    private TextView mCurrentUserName;
    private TextView mCurrentUserStatus;
    private Button mChangeImgBtn;
    private Button mChangeStatusBtn;

    private ProgressDialog mProgressDialog;

    private DatabaseReference mUserRef;
    private FirebaseUser mCurrentUser;

    //Firebase Storage reference
    private StorageReference mImageStorage;

    // For accessing the phone gallery
    private static final int GALLERY_PICK = 1;

    // For random stringBuilder generator
    private static final int MAX_LENGTH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayImage = findViewById(R.id.main_settings_img);
        mCurrentUserName = findViewById(R.id.main_settings_displayName_txt);
        mCurrentUserStatus = findViewById(R.id.main_settings_displayStatus_txt);
        mChangeImgBtn = findViewById(R.id.main_settings_change_img_btn);
        mChangeStatusBtn = findViewById(R.id.main_settings_change_status_btn);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        // Set reference to Users object "node" in Firebase Database
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        // Retrieve User Data from the Firebase in the Settings Activity
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                //String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mCurrentUserName.setText(name);
                mCurrentUserStatus.setText(status);

                // This is to retain Default avatar if the user has not uploaded image
                if(!image.equals("default")){
                    Glide.with(SettingsActivity.this).load(image).into(mDisplayImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Navigate to Status Activity and pass the previous status to the Status Activity
        mChangeStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statusValue = mCurrentUserStatus.getText().toString();
                Intent statusIntent = new Intent(SettingsActivity.this, StatusActivity.class);
                statusIntent.putExtra("status_value", statusValue);
                startActivity(statusIntent);
            }
        });

        // Open phone gallery onClick Button to choose the image
        mChangeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });

    }

    // Once image is selected start Crop Activity, crop img and start SettingActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            // Crop image in square resolution and start Settings Activity
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(SettingsActivity.this);
        }

        // Check that if result is from Crop Activity and getUri of that Crop Activity result
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                initProgressBar();

                Uri resultUri = result.getUri();

                // Get current UID of the user and store  it in a String
                final String currentUserId = mCurrentUser.getUid();

                // As we have already init the StorageReference above, just need to create
                // filePath with the above created Storage reference
                // Then, first store image name with current user id in Firebase Storage
                // Second, store the image link in Firebase Database
                final StorageReference imageFilePath = mImageStorage.child("profile_images").child(currentUserId+".jpg");

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
                                            mProgressDialog.dismiss();
                                            Toast.makeText(SettingsActivity.this,"Profile img changed", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            mProgressDialog.dismiss();
                                            Toast.makeText(SettingsActivity.this,"Error loading img", Toast.LENGTH_LONG).show();
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


    public void initProgressBar(){
        mProgressDialog= new ProgressDialog(SettingsActivity.this);
        mProgressDialog.setTitle("Uploading image...");
        mProgressDialog.setMessage("Please wait while we process the image");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }



    // Generate random string to be used with the image name when uploading to Firebase Storage
    public static String random(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;

        for(int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96)+32);
            randomStringBuilder.append(tempChar);
        }

        return randomStringBuilder.toString();
    }
}