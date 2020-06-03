package com.rupesh.audiohubapp.model;

import java.io.Serializable;

public class Comment implements Serializable {

    private String commenter;
    private String commentId;
    private String createdOn;
    private String image;
    private String comment;

    public Comment(){}

    public Comment(String commenter, String commentId, String createdOn, String image, String comment) {
        this.commenter = commenter;
        this.commentId = commentId;
        this.createdOn = createdOn;
        this.image = image;
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
