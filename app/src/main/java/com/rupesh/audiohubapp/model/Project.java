package com.rupesh.audiohubapp.model;

import java.io.Serializable;

public class Project implements Serializable {

    private String projectName;
    private String createdOn;
    private String creatorID;
    private String pUid;
    //private List<User> members;


    public Project(){}

    public Project(String projectName, String createdOn){
        this.projectName = projectName;
        this.createdOn = createdOn;
    }

    public Project(String projectName, String createdOn, String creatorID, String pUid) {
        this.projectName = projectName;
        this.createdOn = createdOn;
        this.creatorID = creatorID;
        this.pUid = pUid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public  void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", creatorID='" + creatorID + '\'' +
                ", pUid='" + pUid + '\'' +
                '}';
    }
}
