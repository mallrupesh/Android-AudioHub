package com.rupesh.audiohubapp.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityPresenter {

    // Declare instance of firebase authorization
    private FirebaseAuth mAuth;


    public MainActivityPresenter() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentAppUser() {
        return mAuth.getCurrentUser();
    }

    public void appSignOut() {
        mAuth.signOut();
    }
}
