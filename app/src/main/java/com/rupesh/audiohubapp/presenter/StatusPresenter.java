package com.rupesh.audiohubapp.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.activities.StatusActivity;

public class StatusPresenter {

    private DatabaseReference userDatabaseRef;
    private FirebaseUser mCurrentUser;
    private StatusActivity statusActivity;

    public StatusPresenter(StatusActivity statusActivity) {
        this.statusActivity = statusActivity;
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Set reference to Users object "node" in Firebase Database
        userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
    }

    public void setStatus(String userStatus) {
        userDatabaseRef.child("status").setValue(userStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    statusActivity.onSuccessStatusUpdate("Status updated");
                }else{
                    statusActivity.onErrorStatusUpdate("There was some error updating the status");
                }
            }
        });
    }
}
