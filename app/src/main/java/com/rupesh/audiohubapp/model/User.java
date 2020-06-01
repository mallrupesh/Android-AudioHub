package com.rupesh.audiohubapp.model;

import android.text.TextUtils;
import android.util.Patterns;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    /**
     * Used for Login process
     * @param email
     * @param password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Used for Registration process
     * @param name
     * @param email
     * @param password
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    /**
     * Used for application operations
     * @param name
     * @param email
     * @param image
     * @param status
     * @param password
     * @param createdOn
     * @param uid
     */
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
     * @return true if email and password match the given patterns
     *         false if email and password do not match the given patterns
     */
    @Override
    public boolean isValidRegisteredData() {
        return !TextUtils.isEmpty(getEmail())
                && !TextUtils.isEmpty(getName())
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
                && validateUserPassword();
    }

    /**
     * Validates the User's email and password each time before the User login the app
     * @return true if email and password match the given patterns
     *         false if email and password do not match the given patterns
     */
    @Override
    public boolean isValidLoginData() {
        return !TextUtils.isEmpty(getEmail())
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
                //&& validateUserPassword();
    }


    /**
     * Validate the User's password. Uses regex to match the password pattern
     * 1. (?=.*[0-9]) -> password must contain at least 1 digit
     * 2. (?=.*[a-z]) -> password must contain at least 1 lower case letter
     * 3. (?=.*[A-Z]) -> password must contain at least 1 upper case letter
     * 4. (?=.*[*@#$%^&+=]) -> password must contain at least 1 special character
     * 5. (?=\S+$) -> password must not have whitespaces in the entire string
     * 6. .{8,} -> password must be at least 8 places long
     * @return true if password adheres the above conditions
     *         false if password fails to adhere the above conditions.
     */
    public boolean validateUserPassword() {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_SEQUENCE = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*@#$%^&+=])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_SEQUENCE);
        matcher = pattern.matcher(getPassword());
        return matcher.matches();
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

