package com.rupesh.audiohubapp.model;

public class User {
    private String name;
    private String email;
    private Project project;
    private CurrentDate currentDate;


    public User(String name){
        this.name = name;
        currentDate = new CurrentDate();
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public CurrentDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(CurrentDate currentDate) {
        this.currentDate = currentDate;
    }
}
