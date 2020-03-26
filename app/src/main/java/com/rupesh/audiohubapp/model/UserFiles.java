package com.rupesh.audiohubapp.model;

public class UserFiles {
    private String name;
    private String createdOn;
    private String fileUrl;

    // Needed for Firebase operations
    public UserFiles(){}

    public UserFiles(String name, String createdOn, String fileUrl){
        this.name = name;
        this.createdOn = createdOn;
        this.fileUrl = fileUrl;
    }

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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
