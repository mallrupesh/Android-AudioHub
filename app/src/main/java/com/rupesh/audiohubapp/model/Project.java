package com.rupesh.audiohubapp.model;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.Calendar;

public class Project {

    private String projectName;
    private String createdOn;


    public Project(){}

    public Project(String projectName){
        this.projectName = projectName;
    }

    public Project(String projectName, String createdOn) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getCreatedOn() {
        Calendar currentDate = Calendar.getInstance();
        createdOn = DateFormat.getDateInstance().format(currentDate.getTime());
        return createdOn;
    }


    @NonNull
    @Override
    public String toString() {
        return "Projects{" +
                "projectName='" + projectName + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
