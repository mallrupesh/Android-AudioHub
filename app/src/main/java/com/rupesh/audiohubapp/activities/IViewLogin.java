package com.rupesh.audiohubapp.activities;

/**
 * Interface for Login View
 * Protocols/ Contracts for Login Activity
 */
public interface IViewLogin {

    void onLoginSuccess(String message);
    void onLoginError(String message);
}
