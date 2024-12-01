package com.iit.rakshak;

public class Userinfo {

    private int id;
    private String username, email, gender,getMobileNumber,heartrate,rprate,bp,osaturation,currentDate,currentTime;

    public Userinfo(int id, String username, String email, String gender, String getMobileNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.getMobileNumber=getMobileNumber;

    }

    public Userinfo(int id, String username, String email, String gender, String getMobileNumber, String heartrate, String currentDate, String currentTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.getMobileNumber=getMobileNumber;
        this.heartrate=heartrate;
        this.currentDate=currentDate;
        this.currentTime=currentTime;
    }

    public Userinfo(int id, String username, String email, String gender, String getMobileNumber, String heartrate, String rprate, String bp, String osaturation, String currentDate, String currentTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.getMobileNumber=getMobileNumber;
        this.heartrate=heartrate;
        this.rprate=rprate;
        this.bp=bp;
        this.osaturation=osaturation;
        this.currentDate=currentDate;
        this.currentTime=currentTime;
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
    public String heartrate() {
        return heartrate;
    }
    public String rprate() {
        return rprate;
    }
    public String bp() {
        return bp;
    }
    public String osaturation() {
        return osaturation;
    }
    public String currentDate() {
        return currentDate;
    }
    public String currentTime() {
        return currentTime;
    }



}