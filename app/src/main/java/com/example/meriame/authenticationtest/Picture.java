package com.example.meriame.authenticationtest;

import java.util.Date;
import java.util.Vector;

public class Picture {
    private String pseudo;
    private Date date;
    private String uri;
    private Vector<Comment> comments;

    public Picture() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Vector<Comment> getComments() {
        return comments;
    }

    public void setComments(Vector<Comment> comments) {
        this.comments = comments;
    }

    public Picture(String pseudo, Date date, String uri, Vector<Comment> comments) {

        this.pseudo = pseudo;
        this.date = date;
        this.uri = uri;
        this.comments = comments;
    }

    public void AddComments(Comment c){
        comments.add(c);
    }
}
