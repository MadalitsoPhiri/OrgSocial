package com.example.orgsocial.models;

public class CallList {
    private String userID;
    private String userName;
    private String callDate;
    private String userProfileUrl;
    private String CallType;

    public CallList(String userID, String userName, String callDate, String userProfileUrl, String callType) {
        this.userID = userID;
        this.userName = userName;
        this.callDate = callDate;
        this.userProfileUrl = userProfileUrl;
        CallType = callType;
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

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getCallType() {
        return CallType;
    }

    public void setCallType(String callType) {
        CallType = callType;
    }
}