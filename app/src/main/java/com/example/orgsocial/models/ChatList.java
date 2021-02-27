package com.example.orgsocial.models;

import java.util.Date;

public class ChatList implements Comparable<ChatList>{
    private String userID;
    private String userName;
    private String description;
    private Date date;
    private String urlProfile;
    private String Email;
    private String about;
    private String DateJoined;
    private String userPhone;

    public ChatList(String userID, String userName, String description, Date date, String urlProfile, String email, String about, String dateJoined, String userPhone) {
        this.userID = userID;
        this.userName = userName;
        this.description = description;
        this.date = date;
        this.urlProfile = urlProfile;
        Email = email;
        this.about = about;
        DateJoined = dateJoined;
        this.userPhone = userPhone;
    }


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDateJoined() {
        return DateJoined;
    }

    public void setDateJoined(String dateJoined) {
        DateJoined = dateJoined;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public void setUrlProfile(String urlProfile) {
        this.urlProfile = urlProfile;
    }

    @Override
    public int compareTo(ChatList chatList) {
        return getDate().compareTo(chatList.getDate());
    }
}
