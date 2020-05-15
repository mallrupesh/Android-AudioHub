package com.rupesh.audiohubapp.model;

import android.text.TextUtils;
import android.util.Patterns;

import java.io.Serializable;

public class User implements IUser, Serializable {

    private String name;
    private String email;
    private String image;
    private String status;
    private String password;
    private String createdOn;
    private String uid;


    /**
     * Empty constructor for Firebase operation
     */
    public User(){}


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String image, String status, String password, String createdOn, String uid) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.status = status;
        this.password = password;
        this.createdOn = createdOn;
        this.uid = uid;
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

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Validates the User's name, email, and password before registering the User to
     * the app.
     * @return
     */
    @Override
    public boolean isValidRegisteredData() {
        return !TextUtils.isEmpty(getEmail())
                && !TextUtils.isEmpty(getName())
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
                && getPassword().length() > 6;
    }

    /**
     * Validates the User's email and password each time before the User login the app
     * @return
     */
    @Override
    public boolean isValidLoginData() {
        return !TextUtils.isEmpty(getEmail())
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
                && getPassword().length() > 6;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

