package com.rupesh.audiohubapp.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DialogBoxPresenter {

    // Declare instance of firebase authorization
    private FirebaseAuth mAuth;

    public DialogBoxPresenter() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentAppUser() {
        return mAuth.getCurrentUser();
    }
}
