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
import com.rupesh.audiohubapp.presenter.IPresenterRegister;
import com.rupesh.audiohubapp.presenter.RegisterPresenter;
import com.rupesh.audiohubapp.view.IViewRegister;

public class RegisterActivity extends AppCompatActivity implements IViewRegister {

    // Declare UI components instances
    private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCreateBtn;
    private Toolbar mToolbar;

    // Declare Progress dialog
    private ProgressDialog mRegProgress;

    // Declare interface IPresenterProtocol instance
    IPresenterRegister registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Init components
        mDisplayName = findViewById(R.id.reg_display_name);
        mEmail = findViewById(R.id.reg_display_email);
        mPassword = findViewById(R.id.reg_display_password);
        mCreateBtn = findViewById(R.id.reg_create_btn);

        // Init and setup Toolbar
        mToolbar = findViewById(R.id.register_toolbar);
        setActionBar(mToolbar);

        // Init Progress dialog
        mRegProgress = new ProgressDialog(RegisterActivity.this);

        // Init presenter
        registerPresenter = new RegisterPresenter(this);


        // Handle Button event
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterProgress();
                registerPresenter.onRegister(mDisplayName.getText().toString(), mEmail.getText().toString(), mPassword.getText().toString());
            }
        });
    }

    // Sets up Registration dialog
    public void showRegisterProgress(){
        mRegProgress.setTitle("Registering user");
        mRegProgress.setMessage("Please wait while we create your account");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();
    }

    /**
     * Sets up the ActionBar.
     * @param toolbar for the Activity.
     */
    public void setActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);

        try
        {
            getSupportActionBar().setTitle("Create Account");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(NullPointerException eNP){
            Log.v("Register toolbar", eNP.toString());
        }
    }


    /**
     * Executes when the Authorization is success.
     * @param message stating the authorization has been successful.
     */
    @Override
    public void onAuthorizationSuccess(String message) {
        mRegProgress.dismiss();
        Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);

        // Avoids going back to StartActivity once the user is Authorized
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }

    /**
     * Executes when the Authorization fails
     * @param message stating the authorization has failed.
     */
    @Override
    public void onAuthorizationError(String message) {
        mRegProgress.hide();
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
