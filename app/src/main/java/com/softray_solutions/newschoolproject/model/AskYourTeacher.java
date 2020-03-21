
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AskYourTeacher {

    @SerializedName("answer")
    private String mAnswer;
    @SerializedName("content")
    private String mContent;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("ID")
    private String mID;
    @SerializedName("isactive")
    private String mIsactive;
    @SerializedName("level")
    private String mLevel;
    @SerializedName("mail_address")
    private String mMailAddress;
    @SerializedName("name")
    private String mName;
    @SerializedName("row")
    private String mRow;
    @SerializedName("studentID")
    private String mStudentID;
    @SerializedName("subject")
    private String mSubject;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("titleLesson")
    private String mTitleLesson;

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
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

    public String getIsactive() {
        return mIsactive;
    }

    public void setIsactive(String isactive) {
        mIsactive = isactive;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        mLevel = level;
    }

    public String getMailAddress() {
        return mMailAddress;
    }

    public void setMailAddress(String mailAddress) {
        mMailAddress = mailAddress;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRow() {
        return mRow;
    }

    public void setRow(String row) {
        mRow = row;
    }

    public String getStudentID() {
        return mStudentID;
    }

    public void setStudentID(String studentID) {
        mStudentID = studentID;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitleLesson() {
        return mTitleLesson;
    }

    public void setTitleLesson(String titleLesson) {
        mTitleLesson = titleLesson;
    }

}
