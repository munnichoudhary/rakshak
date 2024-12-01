package com.iit.rakshak;

public class UserHeartrate
{
    private int id;
    private String username, email, gender,getMobileNumber,heartrate;

    public UserHeartrate(int id, String username, String email, String gender,String getMobileNumber,String heartrate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.getMobileNumber=getMobileNumber;
        this.heartrate=heartrate;
    }



    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
    public String getMobileNumber() {
        return getMobileNumber;
    }

}
