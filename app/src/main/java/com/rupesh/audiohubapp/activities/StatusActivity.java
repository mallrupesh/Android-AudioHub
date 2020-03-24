package com.rupesh.audiohubapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputLayout currentUserStatus;
    private Button statusUpdateBtn;
    private ProgressDialog mProgressDialog;

    private DatabaseReference userDatabaseRef;
    private FirebaseUser mCurrentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        currentUserStatus = findViewById(R.id.status_input_txt);
        statusUpdateBtn = findViewById(R.id.status_update_btn);

        // Setup the tool bar
        mToolbar = findViewById(R.id.status_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get user's previous status string value from Settings Activity and set it on the EditText
        String prevStatusValue = getIntent().getStringExtra("status_value");
        currentUserStatus.getEditText().setText(prevStatusValue);



        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        // Set reference to Users object "node" in Firebase Database
        userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);


        // Update user status onButtonClick
        statusUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = new ProgressDialog(StatusActivity.this);
                mProgressDialog.setTitle("Saving Changes");
                mProgressDialog.setTitle("Please wait while we save the changes");
                mProgressDialog.show();

                String userStatus = currentUserStatus.getEditText().getText().toString();
                userDatabaseRef.child("status").setValue(userStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mProgressDialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(), "There was some error updating the status",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
