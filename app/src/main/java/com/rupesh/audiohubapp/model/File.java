package com.rupesh.audiohubapp.model;

import java.io.Serializable;

/**
 * File data structure
 */
public class File implements Serializable {
    private String name;
    private String createdOn;
    private String fileUrl;
    private String creatorId;
    private String fileId;

    // Needed for Firebase operations
    public File(){}

    public File(String name, String createdOn, String fileUrl, String creatorId, String fileId){
        this.name = name;
        this.createdOn = createdOn;
        this.fileUrl = fileUrl;
        this.creatorId = creatorId;
        this.fileId = fileId;
    }

    /*--------------------Getters and Setters----------*/

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
