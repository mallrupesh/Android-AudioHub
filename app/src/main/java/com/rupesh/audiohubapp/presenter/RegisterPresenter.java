package com.rupesh.audiohubapp.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.helper.UserHelper;
import com.rupesh.audiohubapp.model.CurrentDate;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.activities.IViewRegister;

import java.util.HashMap;

/**
 * Register Presenter handles app side validation and Firebase register operation
 */
public class RegisterPresenter implements IPresenterRegister {

    // Declare instance of interface IViewProtocol
    private IViewRegister registerView;

    // Declare instance of Firebase authentication
    private FirebaseAuth mAuth;

    // Declare Firebase database reference
    private DatabaseReference mDatabase;

    public RegisterPresenter(IViewRegister registerView) {
        this.registerView = registerView;

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }


    /**
     * Validates user email and password. Then, calls Firebase SDK method to authorise
     * and register User
     * @param name
     * @param email
     * @param password
     */
    @Override
    public void onRegister(final String name, final String email, String password) {
        User user = new User(name, email, password);
        user.setHelper(new UserHelper());
        CurrentDate currentDate = new CurrentDate();
        final String createdDate = currentDate.getDate();

        // Check input validity
        boolean isDataValid = user.isValidRegisteredData();

        // If inputs valid authorize the user
        if (isDataValid) {
            // Validate and authorize user using Firebase SDK Authorization
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // If Authorization success
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = currentUser.getUid();

                        // Create new user under Users node in the database
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                        // Initial default values
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("name", name);
                        userMap.put("email", email);
                        userMap.put("uid",uid);
                        userMap.put("createdOn", createdDate);
                        userMap.put("image", "default");
                        userMap.put("status", "AudioHub Member");
                        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                registerView.onAuthorizationSuccess("Authorization successful");
                            }
                        });
                    } else {
                        // If Authorization fails
                        registerView.onAuthorizationError("Email already in use, try another email");
                    }
                }
            });

        } else {
            // If the user inputs are not valid, show error message
            registerView.onAuthorizationError("Please fill in valid email and password");
        }
    }
}


