package com.example.meriame.authenticationtest;

import java.util.Vector;

public class Place {
    private static  double latitude;
    private static  double longitude;
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

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        Place.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        Place.longitude = longitude;
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
