package com.rupesh.audiohubapp.model;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {

    private String projectName;
    private String createdOn;
    private String creatorID;
    private String projectId;
    private List<String> members;


    public Project(){}

    public Project(String projectName, String createdOn){
        this.projectName = projectName;
        this.createdOn = createdOn;
    }

    public Project(String projectName, String createdOn, String creatorID, String projectId, List<String> members) {
        this.projectName = projectName;
        this.createdOn = createdOn;
        this.creatorID = creatorID;
        this.projectId = projectId;
        this.members = members;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", creatorID='" + creatorID + '\'' +
                ", projectID='" + projectId + '\'' +
                '}';
    }
}
