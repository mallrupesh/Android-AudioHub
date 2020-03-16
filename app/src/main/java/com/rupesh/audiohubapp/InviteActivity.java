package com.rupesh.audiohubapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InviteActivity extends AppCompatActivity {

    private ImageView mProfileImgView;
    private TextView mDisplayUserName;
    private TextView mDisplayUserStatus;
    private Button mInviteButton;

    private ProgressDialog mProgressDialog;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        String userId = getIntent().getStringExtra("user_id");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        mProfileImgView = findViewById(R.id.invite_user_image);
        mDisplayUserName = findViewById(R.id.invite_display_name);
        mDisplayUserStatus = findViewById(R.id.invite_display_status);
        mInviteButton = findViewById(R.id.invite_user_btn);

        setupProgressDialog();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String displayName = dataSnapshot.child("name").getValue().toString();
                String displayStatus = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                mDisplayUserName.setText(displayName);
                mDisplayUserStatus.setText(displayStatus);

                Glide.with(InviteActivity.this).load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                        .into(mProfileImgView);

                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    public void setupProgressDialog(){
        mProgressDialog= new ProgressDialog(InviteActivity.this);
        mProgressDialog.setTitle("Loading user data");
        mProgressDialog.setMessage("Please wait while we load the user");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }
}
