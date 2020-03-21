
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ParentNotificationDataModel {

    @SerializedName("NotificationID")
    private String mNotificationID;
    @SerializedName("StudentID")
    private String mStudentID;
    @SerializedName("StudentName")
    private String mStudentName;
    @SerializedName("StudentToken")
    private String mStudentToken;
    @SerializedName("TeacherName")
    private String mTeacherName;
    @SerializedName("Date")
    private String mTestDate;
    @SerializedName("type")
    private String mType;
    @SerializedName("isOpen")
    private boolean mIsOpen;
    @SerializedName("ProImg")
    private String mProfileImage;

    public String getmProfileImage() {
        return mProfileImage;
    }

    public void setmProfileImage(String mProfileImage) {
        this.mProfileImage = mProfileImage;
    }

    public boolean ismIsOpen() {
        return mIsOpen;
    }

    public void setmIsOpen(boolean mIsOpen) {
        this.mIsOpen = mIsOpen;
    }

    public String getNotificationID() {
        return mNotificationID;
    }

    public void setNotificationID(String notificationID) {
        mNotificationID = notificationID;
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

    public String getStudentToken() {
        return mStudentToken;
    }

    public void setStudentToken(String studentToken) {
        mStudentToken = studentToken;
    }

    public String getTeacherName() {
        return mTeacherName;
    }

    public void setTeacherName(String teacherName) {
        mTeacherName = teacherName;
    }

    public String getTestDate() {
        return mTestDate;
    }

    public void setTestDate(String testDate) {
        mTestDate = testDate;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
