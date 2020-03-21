package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TeacherLibraryModel {


    @SerializedName("File_url")
    private String mFileUrl;
    @SerializedName("ID")
    private String mID;
    @SerializedName("Lesson_Title")
    private String mLessonTitle;
    @SerializedName("linkOfYoutube")
    private String mLinkOfYoutube;
    @SerializedName("SubName")
    private String mSubName;
    @SerializedName("Title")
    private String mTitle;


    public String getFileUrl() {
        return mFileUrl;
    }

    public void setFileUrl(String fileUrl) {
        mFileUrl = fileUrl;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getLessonTitle() {
        return mLessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        mLessonTitle = lessonTitle;
    }

    public String getLinkOfYoutube() {
        return mLinkOfYoutube;
    }

    public void setLinkOfYoutube(String linkOfYoutube) {
        mLinkOfYoutube = linkOfYoutube;
    }


    public String getSubName() {
        return mSubName;
    }

    public void setSubName(String subName) {
        mSubName = subName;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}