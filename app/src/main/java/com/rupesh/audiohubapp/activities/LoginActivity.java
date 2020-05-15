package com.rupesh.audiohubapp.activities;

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

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.presenter.IPresenterLogin;
import com.rupesh.audiohubapp.presenter.LoginPresenter;
import com.rupesh.audiohubapp.view.IViewLogin;

public class LoginActivity extends AppCompatActivity implements IViewLogin {

    // Declare UI components instances
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private Button mLoginBtn;
    private Toolbar mToolbar;

    // Declare ProgressDialog
    private ProgressDialog mLoginProgress;

    // Declare Presenter instance
    IPresenterLogin loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Init and setup Toolbar
        mToolbar = findViewById(R.id.login_toolbar);
        setActionBar(mToolbar);


        // Init Progress dialog
        mLoginProgress = new ProgressDialog(LoginActivity.this);


        // Init components
        mLoginEmail = findViewById(R.id.login_email);
        mLoginPassword = findViewById(R.id.login_password);
        mLoginBtn = findViewById(R.id.login_btn);


        // Init Login presenter
        loginPresenter = new LoginPresenter(this);


        // Handle Button event
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginProgress();
                loginPresenter.onLogin(mLoginEmail.getText().toString(), mLoginPassword.getText().toString());
            }
        });
    }


    /**
     * Sets up Progress Dialog box
     */
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
}
