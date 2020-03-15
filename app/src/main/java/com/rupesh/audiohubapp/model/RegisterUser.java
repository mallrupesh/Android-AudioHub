package com.rupesh.audiohubapp.model;

import android.text.TextUtils;
import android.util.Patterns;

public class RegisterUser implements IUserProtocol {

    private String name;
    private String email;
    private String image;
    private String status;
    private String thumb_image;
    private String password;
    private String createdOn;


    // For Firebase database operation
    public RegisterUser(){}

    public RegisterUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public RegisterUser(String name, String email, String image, String status, String thumb_image, String password, String createdOn) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
        this.password = password;
        this.createdOn = createdOn;
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

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", thumb_image='" + thumb_image + '\'' +
                ", password='" + password + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}

