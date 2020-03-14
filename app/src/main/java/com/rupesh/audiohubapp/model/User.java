package com.rupesh.audiohubapp.model;

public class User {
    private String name;
    private String email;
    private String createOn;


    public User(String name){
        this.name = name;
    }

    // Needed for Firebase operation
    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createOn='" + createOn + '\'' +
                '}';
    }
}
