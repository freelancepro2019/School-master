
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Semester {

    @SerializedName("ClassID")
    private String mClassID;
    @SerializedName("ClassName")
    private String mClassName;
    @SerializedName("ClassTableID")
    private String mClassTableID;
    @SerializedName("LevelName")
    private String mLevelName;
    @SerializedName("RowLevelID")
    private String mRowLevelID;
    @SerializedName("RowName")
    private String mRowName;

    public String getClassID() {
        return mClassID;
    }

    public void setClassID(String classID) {
        mClassID = classID;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public String getClassTableID() {
        return mClassTableID;
    }

    public void setClassTableID(String classTableID) {
        mClassTableID = classTableID;
    }

    public String getLevelName() {
        return mLevelName;
    }

    public void setLevelName(String levelName) {
        mLevelName = levelName;
    }

    public String getRowLevelID() {
        return mRowLevelID;
    }

    public void setRowLevelID(String rowLevelID) {
        mRowLevelID = rowLevelID;
    }

    public String getRowName() {
        return mRowName;
    }

    public void setRowName(String rowName) {
        mRowName = rowName;
    }

    @Override
    public String toString() {
        return getLevelName() + " " + getRowName() + " " + getClassName();
    }
}
