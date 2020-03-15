package com.rupesh.audiohubapp.model;

public class User {
    private String name;
    private String email;
    private String image;
    private String status;
    private String thumb_image;
    private String createdOn;


    public User(){}

    public User(String name, String email, String image, String status, String thumb_image, String createdOn) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
        this.createdOn = createdOn;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", thumb_image='" + thumb_image + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
