package com.rupesh.audiohubapp.model;

public class File {
    private String name;
    private String createdOn;

    public File(String name){
        this.name = name;
    }

    // Needed for Firebase operations
    public File(){}

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
