
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentNotificaionDataModel {

    @SerializedName("Date")
    private String mDate;
    @SerializedName("ID")
    private String mID;
    @SerializedName("isOpen")
    private Boolean mIsOpen;
    @SerializedName("NotificationID")
    private String mNotificationID;
    @SerializedName("StudentID")
    private String mStudentID;
    @SerializedName("StudentName")
    private String mStudentName;
    @SerializedName("StudentToken")
    private String mStudentToken;
    @SerializedName("SubjectID")
    private String mSubjectID;
    @SerializedName("TeacherName")
    private String mTeacherName;
    @SerializedName("type")
    private String mType;
    @SerializedName("ProImg")
    private String mProImage;

    public String getmProImage() {
        return mProImage;
    }

    public void setmProImage(String mProImage) {
        this.mProImage = mProImage;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public Boolean getIsOpen() {
        return mIsOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        mIsOpen = isOpen;
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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
