package com.rupesh.audiohubapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rupesh.audiohubapp.R;

/**
 * The first Activity when the app fires up,
 * only when the user is not logged in
 *
 * Either navigates to LoginActivity or LoginActivity
 */
public class StartActivity extends AppCompatActivity {

    private Button mRegBtn;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mRegBtn = findViewById(R.id.start_reg_btn);
        mLoginBtn = findViewById(R.id.start_login_btn);

        // Register login button to onClickListener for the particular event
        // If clicked navigate to LoginActivity
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Register registration button to onClickListener for the particular event
        // If clicked navigate to RegisterActivity
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

