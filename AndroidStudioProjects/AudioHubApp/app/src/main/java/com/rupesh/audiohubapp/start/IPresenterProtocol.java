package com.rupesh.audiohubapp.view;

public interface IPresenterProtocol {
    void onLogin(String email, String password);
    void onRegister(String name, String email, String password);
}

