package com.example.meriame.authenticationtest;

public class User {
    private String UserName;
    private String Email;

    public User(){

    }

    public User(String UserName, String Email){

        this.UserName=UserName;
        this.Email=Email;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


}
