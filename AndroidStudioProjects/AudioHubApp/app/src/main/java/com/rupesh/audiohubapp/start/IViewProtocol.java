package com.rupesh.audiohubapp.start;

public interface IViewProtocol {

    void onLoginSuccess(String message);
    void onLoginError(String message);
    void onAuthorizationSuccess(String message);
    void onAuthorizationError(String message);

}
