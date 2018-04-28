package com.example.meriame.authenticationtest;

import java.util.Vector;

public class Place {
    private String name;
    private String uri;
    private String status;
    private double latitude;
    private double longitude;
    private String addedBy;
    private Vector<Comment> comments;

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    private double note;
    public Place() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public  double getLatitude() {
        return latitude;
    }

    public  void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public  double getLongitude() {
        return longitude;
    }

    public  void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Vector<Comment> getComments() {
        return comments;
    }

    public void setComments(Vector<Comment> comments) {
        this.comments = comments;
    }

    public void AddComment(Comment c){
        comments.add(c);
    }
}
