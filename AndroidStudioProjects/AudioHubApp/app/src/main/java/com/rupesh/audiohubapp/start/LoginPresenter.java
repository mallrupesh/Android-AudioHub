package com.rupesh.audiohubapp.start;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter implements IPresenterProtocol {


    // Declare IViewProtocol instance
    private IViewProtocol loginView;

    // Declare instance of Firebase authentication
    private FirebaseAuth mAuth;

    public LoginPresenter(IViewProtocol loginView) {
        this.loginView = loginView;

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onLogin(String email, String password) {

        LoginUser user = new LoginUser(email, password);

        boolean isDataValid = user.isValidData();

        if(isDataValid) {
            // Check if the user is signed in
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        loginView.onLoginSuccess("Login success");

                    } else {

                        loginView.onLoginError("Authentication failed, cannot sign in. Please try again");
                    }

                }
            });

        } else {
            loginView.onLoginError("Please fill in valid email and password");
        }

    }


    @Override
    public void onRegister(String name, String email, String password) {}

}
