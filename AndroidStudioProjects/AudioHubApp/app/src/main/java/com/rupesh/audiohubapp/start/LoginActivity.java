package com.rupesh.audiohubapp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rupesh.audiohubapp.MainActivity;
import com.rupesh.audiohubapp.R;

public class LoginActivity extends AppCompatActivity implements IViewProtocol {

    // Declare components instances
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private Button mLoginBtn;
    private Toolbar mToolbar;

    // Declare ProgressDialog
    private ProgressDialog mLoginProgress;

    // Declare Presenter instance
    IPresenterProtocol loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Init and setup Toolbar
        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setActionBar(mToolbar);


        // Init Progress dialog
        mLoginProgress = new ProgressDialog(LoginActivity.this);


        // Init components
        mLoginEmail = (EditText) findViewById(R.id.login_email);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginBtn = (Button) findViewById(R.id.login_btn);


        // Init presenter
        loginPresenter = new LoginPresenter(this);


        // Handle Button event
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoginProgress();

                loginPresenter.onLogin(mLoginEmail.getText().toString(),mLoginPassword.getText().toString());
            }
        });
    }


    // Sets up Login dialog
    public void showLoginProgress(){
        mLoginProgress.setTitle("Login in");
        mLoginProgress.setMessage("Please wait while we check your credentials");
        mLoginProgress.setCanceledOnTouchOutside(false);
        mLoginProgress.show();
    }


    // Sets up ActionBar
    public void setActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);

        try
        {
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(NullPointerException eNP){
            Log.v("Login toolbar", eNP.toString());
        }
    }


    // Executes on login success
    @Override
    public void onLoginSuccess(String message) {
        mLoginProgress.dismiss();
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);

        // Avoids going back to StartActivity once the user is logged in
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    // Executes on login error
    @Override
    public void onLoginError(String message) {
        mLoginProgress.hide();
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onAuthorizationSuccess(String message) {}

    @Override
    public void onAuthorizationError(String message) {}
}









    /*  // Register login button to onClickListener for the particular event
        // If clicked navigate to MainActivity
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve the value of the email and password and check if its empty
                String email = mLoginEmail.getText().toString();
                String password = mLoginPassword.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mLoginProgress.setTitle("Login in");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    loginUser(email, password);

                }


            }
        });*/

    /*private void loginUser(String email, String password) {

        // Check if the user is signed in
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mLoginProgress.dismiss();

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {

                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Authentication failed, cannot sign in. Please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }*/
