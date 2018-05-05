package com.example.meriame.authenticationtest;

import java.util.ArrayList;
import java.util.Vector;

public class Place {
    private String name;
    private String address;
    private String type;
    private String uri;
    private String status;
    private double latitude;
    private double longitude;
    private String addedBy;
    private ArrayList<Comment> comments;

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    private double note;
    public Place() {
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void AddComment(Comment c){
        comments.add(c);
    }

}
