package com.rupesh.audiohubapp.model;

public interface IUser {

    String getName();
    String getEmail();
    String getPassword();
    boolean isValidRegisteredData();
    boolean isValidLoginData();
}

