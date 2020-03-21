
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TeacherNotificationDataModel {

    @SerializedName("AskDate")
    private String mAskDate;
    @SerializedName("askID")
    private String mAskID;
    @SerializedName("NotificationID")
    private String mNotificationID;
    @SerializedName("RowLevelID")
    private String mRowLevelID;
    @SerializedName("StudentName")
    private String mStudentName;
    @SerializedName("StudentToken")
    private String mStudentToken;
    @SerializedName("SubjectID")
    private String mSubjectID;
    @SerializedName("TeacherName")
    private String mTeacherName;
    @SerializedName("imgUser")
    private String mImageUser;
    @SerializedName("IsOpen")
    private Boolean mIsOpen;

    public Boolean getmIsOpen() {
        return mIsOpen;
    }

    public void setmIsOpen(Boolean mIsOpen) {
        this.mIsOpen = mIsOpen;
    }

    public String getmImageUser() {
        return mImageUser;
    }

    public void setmImageUser(String mImageUser) {
        this.mImageUser = mImageUser;
    }

    public String getAskDate() {
        return mAskDate;
    }

    public void setAskDate(String askDate) {
        mAskDate = askDate;
    }

    public String getAskID() {
        return mAskID;
    }

    public void setAskID(String askID) {
        mAskID = askID;
    }

    public String getNotificationID() {
        return mNotificationID;
    }

    public void setNotificationID(String notificationID) {
        mNotificationID = notificationID;
    }

    public String getRowLevelID() {
        return mRowLevelID;
    }

    public void setRowLevelID(String rowLevelID) {
        mRowLevelID = rowLevelID;
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

    public String getSubjectID() {
        return mSubjectID;
    }

    public void setSubjectID(String subjectID) {
        mSubjectID = subjectID;
    }

    public String getTeacherName() {
        return mTeacherName;
    }

    public void setTeacherName(String teacherName) {
        mTeacherName = teacherName;
    }

}
