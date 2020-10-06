package com.rupesh.audiohubapp.activities;

/**
 * Interface for Register View
 * Protocols/ Contracts for Register Activity
 */

public interface IViewRegister {

    void onAuthorizationSuccess(String message);
    void onAuthorizationError(String message);
}
