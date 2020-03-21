package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentList {

    @SerializedName("FatherID")
    private String mFatherID;
    @SerializedName("FatherName")
    private String mFatherName;
    @SerializedName("FullName")
    private String mFullName;
    @SerializedName("StudentID")
    private String mStudentID;
    @SerializedName("StudentName")
    private String mStudentName;
    @SerializedName("Img")
    private String profileImage;
    private boolean selected = false;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFatherID() {
        return mFatherID;
    }

    public void setFatherID(String fatherID) {
        mFatherID = fatherID;
    }

    public String getFatherName() {
        return mFatherName;
    }

    public void setFatherName(String fatherName) {
        mFatherName = fatherName;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getStudentID() {
        return mStudentID;
    }

    public void setStudentID(String studentID) {
        mStudentID = studentID;
    }

    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String studentName) {
        mStudentName = studentName;
    }

}