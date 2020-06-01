package com.rupesh.audiohubapp.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.activities.IViewLogin;

public class LoginPresenter implements IPresenterLogin {


    // Declare IViewProtocol instance
    private IViewLogin loginView;

    // Declare instance of Firebase authentication
    private FirebaseAuth mAuth;

    public LoginPresenter(IViewLogin loginView) {
        this.loginView = loginView;

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onLogin(String email, String password) {
        User user = new User(email, password);
        boolean isDataValid = user.isValidLoginData();

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
}
