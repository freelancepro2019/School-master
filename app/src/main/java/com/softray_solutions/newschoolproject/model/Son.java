
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Son {

    @SerializedName("Class_ID")
    private String mClassID;
    @SerializedName("ID")
    private String mID;
    @SerializedName("LastLogin")
    private String mLastLogin;
    @SerializedName("Name")
    private String mName;
    @SerializedName("R_L_ID")
    private String mRLID;
    @SerializedName("Token")
    private String mToken;
    @SerializedName("SchoolID")
    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public String getClassID() {
        return mClassID;
    }

    public void setClassID(String classID) {
        mClassID = classID;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getLastLogin() {
        return mLastLogin;
    }

    public void setLastLogin(String lastLogin) {
        mLastLogin = lastLogin;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRLID() {
        return mRLID;
    }

    public void setRLID(String rLID) {
        mRLID = rLID;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

}
