package com.example.orgsocial.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firestore.v1.DocumentTransform;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userPhotoUrl;
    private String description;
    private String userGender;
    private boolean Status = false;
    private boolean Verified = false;
    private Object DateJoined = ServerValue.TIMESTAMP;

    public User(){

    }

    public User(String photo, String about){
        this.userPhotoUrl = photo;
        this.description = about;
    }

    public User(String userId, String userName, String userEmail, String userPhone){
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public User(String userId, String userName, String userEmail, String userPhone, String userPhotoUrl, String description, boolean status,boolean verified) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPhotoUrl = userPhotoUrl;
        this.description = description;
        this.Status = status;

        this.Verified = verified;
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


    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public void setVerified(boolean verified) {
        Verified = verified;
    }

    public Object getDateJoined() {
        return DateJoined;
    }

    public void setDateJoined(FieldValue dateJoined) {
        DateJoined = dateJoined;
    }
    public boolean isVerified() {
        return Verified;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }


}