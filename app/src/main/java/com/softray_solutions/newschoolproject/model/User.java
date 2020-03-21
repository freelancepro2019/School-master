package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private String mId;
    @SerializedName("img")
    private String mImg;
    @SerializedName("Mail")
    private String mMail;
    @SerializedName("name")
    private String mName;
    @SerializedName("SchoolID")
    private String mSchoolID;
    @SerializedName("type")
    private String mType;
    @SerializedName("sessionToken")
    private String sessionToken;
    @SerializedName("Mobile")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImg() {
        return mImg;
    }

    public void setImg(String img) {
        mImg = img;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String mail) {
        mMail = mail;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSchoolID() {
        return mSchoolID;
    }

    public void setSchoolID(String schoolID) {
        mSchoolID = schoolID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
