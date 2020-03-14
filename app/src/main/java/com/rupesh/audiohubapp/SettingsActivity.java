package com.rupesh.audiohubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView mDisplayImage;
    private TextView mCurrentUserName;
    private TextView mCurrentUserStatus;
    private Button mChangeImgBtn;
    private Button mChangeStatusBtn;

    private DatabaseReference mUserRef;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayImage = findViewById(R.id.main_settings_img);
        mCurrentUserName = findViewById(R.id.main_settings_displayName_txt);
        mCurrentUserStatus = findViewById(R.id.main_settings_displayStatus_txt);
        mChangeImgBtn = findViewById(R.id.main_settings_change_img_btn);
        mChangeStatusBtn = findViewById(R.id.main_settings_change_status_btn);


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
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mCurrentUserName.setText(name);
                mCurrentUserStatus.setText(status);
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
    }
}