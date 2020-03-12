package com.rupesh.audiohubapp.view;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterPresenter implements IPresenterProtocol {

    private IViewProtocol registerView;

    // Declare instance of firebase authentication
    private FirebaseAuth mAuth;


    private DatabaseReference mDatabase;

    public RegisterPresenter(IViewProtocol registerView) {
        this.registerView = registerView;

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onRegister(final String name, String email, String password) {

        RegisterUser user = new RegisterUser(name, email, password);

        boolean isDataValid = user.isValidData();

        if (isDataValid) {
            // Validate and authorize user to Firebase Authorization database
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Authorization success

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = currentUser.getUid();

                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Members").child(uid);

                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("name", name);
                        userMap.put("address", "default");
                        userMap.put("hobby", "default");
                        mDatabase.setValue(userMap);


                        registerView.onAuthorizationSuccess("Authorization successful");
                    } else {
                        // Authorization fails
                        registerView.onAuthorizationError("Unable to register");
                    }
                }
            });

        } else {
            registerView.onAuthorizationError("Please fill in valid email and password");
        }

    }

    @Override
    public void onLogin(String email, String password) {}
}


