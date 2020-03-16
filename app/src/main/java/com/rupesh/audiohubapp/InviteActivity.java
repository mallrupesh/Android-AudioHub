package com.rupesh.audiohubapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class InviteActivity extends AppCompatActivity {

    private static final int NOT_IN_PROJECT = 0;
    private static final int IN_PROJECT = 1;
    private int currentState;

    private ImageView mProfileImgView;
    private TextView mDisplayUserName;
    private TextView mDisplayUserStatus;
    private Button mInviteButton;

    private ProgressDialog mProgressDialog;

    private DatabaseReference mDatabaseRef;

    private DatabaseReference mInviteDatabaseRef;

    private FirebaseUser mCurrentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        // Get the uid from the AllUserActivity
        final String user_id = getIntent().getStringExtra("user_id");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mInviteDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Invite_Requests");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mProfileImgView = findViewById(R.id.invite_user_image);
        mDisplayUserName = findViewById(R.id.invite_display_name);
        mDisplayUserStatus = findViewById(R.id.invite_display_status);
        mInviteButton = findViewById(R.id.invite_user_btn);

        setupProgressDialog();


        // Get the user's image, name and status after clicking the item in AllUsersActivity
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

        mInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentState == NOT_IN_PROJECT){
                    mInviteDatabaseRef.child(mCurrentUser.getUid()).child(user_id).child("request_type")
                            .setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                mInviteDatabaseRef.child(user_id).child(mCurrentUser.getUid())
                                        .child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(InviteActivity.this, "Invitation sent", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }else{
                                Toast.makeText(InviteActivity.this, "Failed to send invitation", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
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
