package com.rupesh.audiohubapp.model;

import androidx.annotation.NonNull;

public class Project {

    private String projectName;
    private String createdOn;


    public Project(){}

    public Project(String projectName){
        this.projectName = projectName;
    }

    public Project(String projectName, String createdOn) {
        this.projectName = projectName;
        this.createdOn = createdOn;
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
        //Calendar currentDate = Calendar.getInstance();
        //createdOn = DateFormat.getDateInstance().format(currentDate.getTime());
        //return createdOn;
        return createdOn;
    }


    @NonNull
    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
