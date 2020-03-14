package com.rupesh.audiohubapp.model;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.DateFormat;
import java.util.Calendar;

public class RegisterUser implements IUserProtocol {

    private String name;
    private String email;
    private String password;
    private String createdOn;

    public RegisterUser(){}

    public RegisterUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isValidData() {
        return !TextUtils.isEmpty(getEmail())
                && !TextUtils.isEmpty(getName())
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
                && getPassword().length() > 6;
    }

    public String getCreatedOn() {
        Calendar currentDate = Calendar.getInstance();
        createdOn = DateFormat.getDateInstance().format(currentDate.getTime());
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}

