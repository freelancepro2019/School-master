package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Subject {
    @SerializedName("LevelName")
    private String mLevelName;
    @SerializedName("RowName")
    private String mRowName;
    @SerializedName(value = "ClassID", alternate = "Class_ID")
    private String mClassID;
    @SerializedName("ClassSubID")
    private String mClassSubID;
    @SerializedName(value = "ClassSubName", alternate = "SubjectName")
    private String mClassSubName;
    @SerializedName(value = "SRowLevelID", alternate = {"RowLevelID"})
    private String mSRowLevelID;
    @SerializedName(value = "subjectID", alternate = {"SubjectID"})
    private String mSubjectID;

    public String getmSubjectID() {
        return mSubjectID;
    }

    public void setmSubjectID(String mSubjectID) {
        this.mSubjectID = mSubjectID;
    }

    public String getClassID() {
        return mClassID;
    }

    public void setClassID(String classID) {
        mClassID = classID;
    }

    public String getClassSubID() {
        return mClassSubID;
    }

    public void setClassSubID(String classSubID) {
        mClassSubID = classSubID;
    }

    public String getClassSubName() {
        return mClassSubName;
    }

    public void setClassSubName(String classSubName) {
        mClassSubName = classSubName;
    }

    public String getSRowLevelID() {
        return mSRowLevelID;
    }

    public void setSRowLevelID(String sRowLevelID) {
        mSRowLevelID = sRowLevelID;
    }


    public String getmLevelName() {
        return mLevelName;
    }

    public void setmLevelName(String mLevelName) {
        this.mLevelName = mLevelName;
    }

    public String getmRowName() {
        return mRowName;
    }

    public void setmRowName(String mRowName) {
        this.mRowName = mRowName;
    }

    @Override
    public String toString() {
        return getClassSubName();
    }
}
