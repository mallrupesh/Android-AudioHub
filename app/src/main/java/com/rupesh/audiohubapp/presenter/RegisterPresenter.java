package com.rupesh.audiohubapp.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.model.CurrentDate;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.view.IViewProtocol;

import java.util.HashMap;

public class RegisterPresenter implements IPresenterProtocol {

    // Declare instance of interface IViewProtocol
    private IViewProtocol registerView;

    // Declare instance of Firebase authentication
    private FirebaseAuth mAuth;

    // Declare Firebase database reference
    private DatabaseReference mDatabase;

    public RegisterPresenter(IViewProtocol registerView) {
        this.registerView = registerView;

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onRegister(final String name, final String email, String password) {

        // Create new user with the provided name, email and password entered by the user
        User user = new User(name, email, password);

        // Get current date
        CurrentDate currentDate = new CurrentDate();
        final String createdDate = currentDate.getDate();

        // Check if the user inputs are valid
        boolean isDataValid = user.isValidData();

        // If inputs valid authorize the user
        if (isDataValid) {
            // Validate and authorize user to Firebase Authorization database
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // If Authorization success
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = currentUser.getUid();

                        // Point to the newly registered user and add initial default values in the Firebase database
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
                        registerView.onAuthorizationError("Unable to register");
                    }
                }
            });

        } else {
            // If the user inputs are not valid, show error message
            registerView.onAuthorizationError("Please fill in valid email and password");
        }
    }

    @Override
    public void onLogin(String email, String password) {}
}


