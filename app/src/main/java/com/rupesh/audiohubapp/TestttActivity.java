package com.rupesh.audiohubapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestttActivity extends AppCompatActivity {

    TextView textViewTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testtt);

        // Get the project id from the ProjectMemberActivity
        final String project_id = getIntent().getStringExtra("project_id");

        textViewTest = findViewById(R.id.textViewTest01);

        textViewTest.setText(project_id);
    }
}
