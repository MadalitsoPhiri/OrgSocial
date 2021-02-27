package com.example.orgsocial.models;

import android.app.MediaRouteActionProvider;

import androidx.annotation.Keep;

import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firestore.v1.DocumentTransform;


import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Group implements Serializable {

    private String groupPhotoUrl;
    private String groupId;
    private String groupSubject;
    @ServerTimestamp Date dateCreated;



    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Group() {
    }


    public Group(String groupPhotoUrl, String groupId, String groupSubject) {

        this.groupPhotoUrl = groupPhotoUrl;
        this.groupId = groupId;
        this.groupSubject = groupSubject;



    }


    public String getGroupPhotoUrl() {
        return groupPhotoUrl;
    }

    public void setGroupPhotoUrl(String groupPhotoUrl) {
        this.groupPhotoUrl = groupPhotoUrl;
    }

    public String getGroupSubject() {
        return groupSubject;
    }

    public void setGroupSubject(String groupSubject) {
        this.groupSubject = groupSubject;
    }



    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


}
