package com.rupesh.audiohubapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.presenter.SettingsPresenter;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView mDisplayImage;
    private TextView mCurrentUserName;
    private TextView mCurrentUserStatus;
    private TextView mCurrentUserEmail;
    private Button mChangeImgBtn;
    private Button mChangeStatusBtn;

    private ProgressDialog mProgressDialog;

    // For accessing the phone gallery
    private static final int GALLERY_PICK = 1;

    private SettingsPresenter settingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayImage = findViewById(R.id.main_settings_img);
        mCurrentUserName = findViewById(R.id.main_settings_displayName_txt);
        mCurrentUserStatus = findViewById(R.id.main_settings_displayStatus_txt);
        mCurrentUserEmail = findViewById(R.id.main_settings_displayEmail_txt);
        mChangeImgBtn = findViewById(R.id.main_settings_change_img_btn);
        mChangeStatusBtn = findViewById(R.id.main_settings_change_status_btn);

        settingsPresenter = new SettingsPresenter(this);
        settingsPresenter.setUserImg();

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

    public void initUI(String name, String status, String img, String email) {
        mCurrentUserName.setText(name);
        mCurrentUserStatus.setText(status);
        mCurrentUserEmail.setText(email);
        // This is to retain Default avatar if the user has not uploaded image
        if(!img.equals("default")){
            Glide.with(SettingsActivity.this).load(img).into(mDisplayImage);
        }
    }

    // Once image is selected start Crop Activity, crop img and start SettingActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        settingsPresenter.uploadImage(requestCode,resultCode,data);
    }

    public void onSuccessImgUpload(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(SettingsActivity.this,message, Toast.LENGTH_LONG).show();
    }

    public void onErrorImgUpload(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(SettingsActivity.this,message, Toast.LENGTH_LONG).show();
    }
    
    public void setupProgressDialog(){
        mProgressDialog= new ProgressDialog(SettingsActivity.this);
        mProgressDialog.setTitle("Uploading image...");
        mProgressDialog.setMessage("Please wait while we process the image");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }
}