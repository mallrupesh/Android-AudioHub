package com.rupesh.audiohubapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.presenter.StatusPresenter;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputLayout currentUserStatus;
    private Button statusUpdateBtn;
    private ProgressDialog mProgressDialog;

    //private DatabaseReference userDatabaseRef;
    //private FirebaseUser mCurrentUser;

    private StatusPresenter statusPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        currentUserStatus = findViewById(R.id.status_input_txt);
        statusUpdateBtn = findViewById(R.id.status_update_btn);

        // Setup the tool bar
        mToolbar = findViewById(R.id.status_appBar);
        setupToolBar();

        statusPresenter = new StatusPresenter(this);

        // Get user's previous status string value from Settings Activity and set it on the EditText
        String prevStatusValue = getIntent().getStringExtra("status_value");
        currentUserStatus.getEditText().setText(prevStatusValue);

        // Update user status onButtonClick
        statusUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupProgressDialog();
                statusPresenter.setStatus(currentUserStatus.getEditText().getText().toString());
            }
        });
    }

    public void onSuccessStatusUpdate(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(StatusActivity.this, message,Toast.LENGTH_LONG).show();
    }

    public void onErrorStatusUpdate(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(StatusActivity.this, message,Toast.LENGTH_LONG).show();
    }

    public void setupToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupProgressDialog() {
        mProgressDialog = new ProgressDialog(StatusActivity.this);
        mProgressDialog.setTitle("Saving Changes");
        mProgressDialog.setTitle("Please wait while we save the changes");
        mProgressDialog.show();
    }
}
