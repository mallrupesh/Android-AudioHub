package com.rupesh.audiohubapp.start;

public interface IPresenterProtocol {
    void onLogin(String email, String password);
    void onRegister(String name, String email, String password);
}

