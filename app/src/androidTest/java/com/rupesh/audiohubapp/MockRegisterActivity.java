package com.rupesh.audiohubapp;

import android.util.Log;

import com.rupesh.audiohubapp.activities.IViewRegister;

public class MockRegisterActivity implements IViewRegister {
    @Override
    public void onAuthorizationSuccess(String message) {
        Log.d("MESSAGE_", message);
    }

    @Override
    public void onAuthorizationError(String message) {
        Log.d("MESSAGE_", message);
    }
}
