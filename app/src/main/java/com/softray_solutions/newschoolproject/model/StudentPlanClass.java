package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentPlanClass {
    @SerializedName("Content")
    private String mContent = "mafesh";
    @SerializedName("FileAttach")
    private String mFileAttach = "mafesh";
    @SerializedName("Object")
    private String mObject = "mafesh";
    @SerializedName("teach")
    private String mTeach = "mafesh";

    private boolean colored = false;

    public StudentPlanClass(String mObject, boolean colored, String content) {
        this.mObject = mObject;
        this.colored = colored;
        this.mContent = content;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getFileAttach() {
        return mFileAttach;
    }

    public void setFileAttach(String fileAttach) {
        mFileAttach = fileAttach;
    }

    public String getObject() {
        return mObject;
    }

    public void setObject(String object) {
        mObject = object;
    }

    public String getTeach() {
        return mTeach;
    }

    public void setTeach(String teach) {
        mTeach = teach;
    }

}