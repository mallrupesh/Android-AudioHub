package com.rupesh.audiohubapp.model;

import java.io.Serializable;
import java.util.HashMap;

public class Project implements Serializable {

    private String projectName;
    private String createdOn;
    private String creatorId;
    private String projectId;
    //private HashMap<String, Object> members;


    public Project(){}

    public Project(String projectName, String createdOn){
        this.projectName = projectName;
        this.createdOn = createdOn;
    }

    public Project(String projectName, String createdOn, String creatorId, String projectId, HashMap<String, Object> members) {
        this.projectName = projectName;
        this.createdOn = createdOn;
        this.creatorId = creatorId;
        this.projectId = projectId;
        //this.members = members;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

   /* public HashMap<String, Object> getMembers() {
        return members;
    }

    public void setMembers(HashMap<String, Object> members) {
        this.members = members;
    }*/

}
