package com.example.orgsocial.models;

import com.google.firebase.database.ServerValue;

import java.io.Serializable;

public class GroupUser implements Serializable {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userPhotoUrl;
    private String description;
    private Boolean admin;
    private Boolean creator;
    private String userGender;
    private Object DateJoined = ServerValue.TIMESTAMP;

    public GroupUser(String userId, String userName, String userEmail, String userPhone, String userPhotoUrl, String description, Boolean admin, Boolean creator) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPhotoUrl = userPhotoUrl;
        this.description = description;
        this.admin = admin;
        this.creator = creator;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getCreator() {
        return creator;
    }

    public void setCreator(Boolean creator) {
        this.creator = creator;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Object getDateJoined() {
        return DateJoined;
    }

    public void setDateJoined(Object dateJoined) {
        DateJoined = dateJoined;
    }
}
