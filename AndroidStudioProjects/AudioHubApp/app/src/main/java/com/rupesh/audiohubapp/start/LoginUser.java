package com.rupesh.audiohubapp.view;

import android.text.TextUtils;
import android.util.Patterns;

public class LoginUser implements IUserProtocol {

    private String email;
    private String password;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
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
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
                && getPassword().length() > 6;
    }

    @Override
    public String getName(){return null;}
}

