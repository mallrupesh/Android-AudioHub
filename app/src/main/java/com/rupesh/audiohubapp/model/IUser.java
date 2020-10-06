package com.rupesh.audiohubapp.model;

/**
 * Protocols/ Contracts for User model
 */
public interface IUser {

    String getName();
    String getEmail();
    String getPassword();
    boolean isValidRegisteredData();
    boolean isValidLoginData();
}

