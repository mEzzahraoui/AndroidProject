package com.example.meriame.authenticationtest;

public class Comment {
    private String userName;
    private String userComment;

    public Comment(){

    }

    public Comment(String userName, String userComment) {
        this.userName = userName;
        this.userComment = userComment;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getUserName() {

        return userName;
    }

    public String getUserComment() {
        return userComment;
    }
}
