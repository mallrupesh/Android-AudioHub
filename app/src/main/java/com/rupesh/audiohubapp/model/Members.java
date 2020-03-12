package com.rupesh.audiohubapp.model;

public class Members {
    String name;
    String hobby;
    String address;

    public Members(String name, String hobby, String address) {
        this.name = name;
        this.hobby = hobby;
        this.address = address;
    }

    // Primary constructor is needed for Firebase operation
    public Members(){}

    @Override
    public String toString() {
        return "Members{" +
                "name='" + name + '\'' +
                ", hobby='" + hobby + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
