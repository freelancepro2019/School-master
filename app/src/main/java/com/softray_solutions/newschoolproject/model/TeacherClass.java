
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TeacherClass {
    @SerializedName("class")
    private String mTeacherClass;
    @SerializedName("level")
    private String mLevel;
    @SerializedName("Object")
    private String mObject;
    @SerializedName("row")
    private String mRow;

    public TeacherClass(String mObject, String mTeacherClass, String mLevel, String mRow) {
        this.mTeacherClass = mTeacherClass;
        this.mLevel = mLevel;
        this.mObject = mObject;
        this.mRow = mRow;
    }

    public String getTeacherClass() {
        return mTeacherClass;
    }

    public void setClass(String teacherClass) {
        mTeacherClass = teacherClass;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        mLevel = level;
    }

    public String getObject() {
        return mObject;
    }

    public void setObject(String object) {
        mObject = object;
    }

    public String getRow() {
        return mRow;
    }

    public void setRow(String row) {
        mRow = row;
    }

}
