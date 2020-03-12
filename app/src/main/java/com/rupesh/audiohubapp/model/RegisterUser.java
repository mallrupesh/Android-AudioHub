package com.rupesh.audiohubapp.model;

import android.text.TextUtils;
import android.util.Patterns;

public class RegisterUser implements IUserProtocol {

    private String name;
    private String email;
    private String password;

    public RegisterUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isValidData() {
        return !TextUtils.isEmpty(getEmail())
                && !TextUtils.isEmpty(getName())
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
                && getPassword().length() > 6;
    }
}

