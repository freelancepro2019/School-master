package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

public class TeacherPlanClass {

    @SerializedName("Object")
    private String mObject = "mafesh";
    private String level = "mafesh";
    private String row = "mafesh";
    @SerializedName("class")
    private String mClass = "mafesh";
    @SerializedName("FileAttach")
    private String fileAttach = "mafesh";
    @SerializedName("Content")
    private String content = "mafesh";
    private boolean colored = false;

    public TeacherPlanClass(String mObject, boolean colored, String content) {
        this.mObject = mObject;
        this.colored = colored;
        this.content = content;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public String getmObject() {
        return mObject;
    }

    public String getLevel() {
        return level;
    }

    public String getRow() {
        return row;
    }

    public String getmClass() {
        return mClass;
    }

    public String getFileAttach() {
        return fileAttach;
    }

    public String getContent() {
        return content;
    }
}
