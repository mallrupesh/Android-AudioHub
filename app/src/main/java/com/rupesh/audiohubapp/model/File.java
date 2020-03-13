package com.rupesh.audiohubapp.model;

public class File {
    private String name;
    private CurrentDate currentDate;

    public File(String name){
        this.name = name;
        currentDate = new CurrentDate();
    }

    // Needed for Firebase operations
    public File(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrentDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(CurrentDate currentDate) {
        this.currentDate = currentDate;
    }
}
