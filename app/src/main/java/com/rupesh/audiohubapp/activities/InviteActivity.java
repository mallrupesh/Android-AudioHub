/*
package com.rupesh.audiohubapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.model.User;

public class InviteActivity extends AppCompatActivity {

    private static final int NOT_IN_PROJECT = 0;
    private static final int REQUEST_SENT = 1;
    private static final int REQUEST_RECEIVED = 2;
    private static final int IN_PROJECT = 3;

    private int currentState;
    private String currentProject;

    private ImageView mProfileImgView;
    private TextView mDisplayUserName;
    private TextView mDisplayUserStatus;
    private Button mInviteButton;
    private Button mDeclineButton;

    private ProgressDialog mProgressDialog;

    private DatabaseReference userDatabaseRef;

    private DatabaseReference inviteDatabaseRef;

    private DatabaseReference projectDatabaseRef;

    private FirebaseUser mCurrentUser;

    private User userSerial;

    private Boolean showCancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

//*
// Get the user uid from the AllUserActivity
        final String user_id = getIntent().getStringExtra("user_id");

        // Get the project uid from the AllUserActivity
        final String project_id = getIntent().getStringExtra("project_id");


        userSerial = (User) getIntent().getSerializableExtra("user");

        showCancelButton = (Boolean) getIntent().getBooleanExtra("fromProjectActivity",false);



userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userSerial.getUid());
        inviteDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Invite_Requests");
        projectDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Projects");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentState = NOT_IN_PROJECT;


        mProfileImgView = findViewById(R.id.invite_user_image);
        mDisplayUserName = findViewById(R.id.invite_display_name);
        mDisplayUserStatus = findViewById(R.id.invite_display_status);
        mInviteButton = findViewById(R.id.invite_user_btn);
        mDeclineButton= findViewById(R.id.invite_decline_user_btn);

        //setupProgressDialog();

        if (showCancelButton) {
            mDeclineButton.setVisibility(View.GONE);
        } else {
            mDeclineButton.setVisibility(View.VISIBLE);
        }

        mDisplayUserName.setText(userSerial.getName());
        mDisplayUserStatus.setText(userSerial.getStatus());


        Glide.with(InviteActivity.this).load(userSerial.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                .into(mProfileImgView);



        mInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        mDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        // Get the user's image, name and status after clicking the item in AllUsersActivity
        userDatabaseRef.addValueEventListener(new ValueEventListener() {
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

                // ------------- ADD MEMBERS TO PROJECT AND REQUEST, ACCEPT/DECLINE MECHANISM---------//

                inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // Check the invite database point to current user node and check the REQUEST TYPE (sent/received)
                        if(dataSnapshot.hasChild(user_id)){


                            String request_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();

                            // If the request type is SENT, current user received the REQUEST from other user
                            // Change the invite button text to ACCEPT INVITATION
                            if(request_type.equals("received")){

                                mInviteButton.setEnabled(true);
                                currentState = REQUEST_RECEIVED;
                                mInviteButton.setText("ACCEPT INVITATION");


                            }else {

                                // If the current current user sent the request to other user,
                                // Change the invite button to CANCEL REQUEST
                                if (request_type.equals("sent")) {

                                    mInviteButton.setEnabled(true);
                                    currentState = REQUEST_SENT;
                                    mInviteButton.setText("CANCEL REQUEST");
                                }
                            }

                            mProgressDialog.dismiss();

                        }else {
                                // If the project already has other member id,
                                projectDatabaseRef.child("member").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild(user_id)){
                                            currentState = IN_PROJECT;
                                            mInviteButton.setText("LEAVE PROJECT");
                                        }
                                        mProgressDialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        mProgressDialog.dismiss();
                                    }
                                });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Disable the invite button, when the request has been made to avoid processing duplicate query
                mInviteButton.setEnabled(false);


                // -----------------SEND REQUEST -> currentState = NOT_IN_PROJECT--------------------//

                if(currentState == NOT_IN_PROJECT){
                    inviteDatabaseRef.child(mCurrentUser.getUid()).child(user_id).child("request_type")
                            .setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                inviteDatabaseRef.child(user_id).child(mCurrentUser.getUid())
                                        .child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        inviteDatabaseRef.child(user_id).child(mCurrentUser.getUid()).child("project_type").setValue(project_id);
                                        mInviteButton.setEnabled(true);
                                        currentState = REQUEST_SENT;
                                        mInviteButton.setText("CANCEL REQUEST");
                                    }
                                });

                            }else{
                                Toast.makeText(InviteActivity.this, "Failed to send invitation", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                // --------------------CANCEL REQUEST -> currentState = INVITATION_SENT---------------------//

                if(currentState == REQUEST_SENT){
                    inviteDatabaseRef.child(mCurrentUser.getUid()).child(user_id).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    inviteDatabaseRef.child(user_id).child(mCurrentUser.getUid())
                                            .child("request_type").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mInviteButton.setEnabled(true);
                                            currentState = NOT_IN_PROJECT;
                                            mInviteButton.setText("SEND REQUEST");
                                        }
                                    });
                                }
                            });
                }

                // --------------------ACCEPT REQUEST -> currentState = INVITATION_RECEIVED---------------------
                if (currentState == REQUEST_RECEIVED){


                    inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(user_id)){
                                currentProject = dataSnapshot.child(user_id).child("project_type").getValue().toString();

                                projectDatabaseRef.child(currentProject).child("members").push().setValue(mCurrentUser.getUid())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                inviteDatabaseRef.child(mCurrentUser.getUid()).child(user_id).removeValue();
                                                inviteDatabaseRef.child(user_id).child(mCurrentUser.getUid())
                                                        .child("request_type").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mInviteButton.setEnabled(true);
                                                        currentState = IN_PROJECT;
                                                        mInviteButton.setText("LEAVE PROJECT");
                                                    }
                                                });
                                            }

                                        });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                    projectDatabaseRef.child("members").setValue(mCurrentUser.getUid()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            inviteDatabaseRef.child(mCurrentUser.getUid()).child(user_id).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            inviteDatabaseRef.child(user_id).child(mCurrentUser.getUid())
                                                    .child("request_type").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mInviteButton.setEnabled(true);
                                                    currentState = IN_PROJECT;
                                                    mInviteButton.setText("LEAVE PROJECT");
                                                }
                                            });
                                        }
                                    });

                        }

                    });

                }

                //----------------------------LEAVE PROJECT -> current state = IN PROJECT-----------------------------

                if(currentState == IN_PROJECT){
                    //String projectUid = projectDatabaseRef.getKey();
                    projectDatabaseRef.child("Projects").child("member").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mInviteButton.setEnabled(true);
                            currentState = NOT_IN_PROJECT;
                            mInviteButton.setText("SEND REQUEST");
                        }
                    });
                }
            }
        });

        //--------------------------DECLINE REQUEST -> current state = NOT IN PROJECT---------------------------------

        mDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDeclineButton.setEnabled(true);

                if(currentState == REQUEST_RECEIVED) {

                    inviteDatabaseRef.child(mCurrentUser.getUid()).child(user_id).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    inviteDatabaseRef.child(user_id).child(mCurrentUser.getUid())
                                            .child("request_type").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mDeclineButton.setEnabled(false);
                                        }
                                    });
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
*/
