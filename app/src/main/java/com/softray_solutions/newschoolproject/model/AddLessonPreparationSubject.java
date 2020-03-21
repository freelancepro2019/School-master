package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddLessonPreparationSubject {

    @SerializedName("LevelName")
    private String mLevelName;
    @SerializedName("RowLevelID")
    private String mRowLevelID;
    @SerializedName("RowName")
    private String mRowName;
    @SerializedName("SubjectID")
    private String mSubjectID;
    @SerializedName("SubjectName")
    private String mSubjectName;

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

    public String getSubjectID() {
        return mSubjectID;
    }

    public void setSubjectID(String subjectID) {
        mSubjectID = subjectID;
    }

    public String getSubjectName() {
        return mSubjectName;
    }

    public void setSubjectName(String subjectName) {
        mSubjectName = subjectName;
    }

    @Override
    public String toString() {
        return getSubjectName() + " - " + getLevelName() + " - " + getRowName();
    }
}
