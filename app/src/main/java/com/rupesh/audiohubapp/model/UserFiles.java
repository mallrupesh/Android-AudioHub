package com.rupesh.audiohubapp.model;

public class UserFiles {
    private String name;
    private String createdOn;

    public UserFiles(String name){
        this.name = name;
    }

    // Needed for Firebase operations
    public UserFiles(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
