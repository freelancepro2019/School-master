
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Lesson {

    @SerializedName("LessonDate")
    private String lessonDate;
    @SerializedName("LessonID")
    private String lessonID;
    @SerializedName("LessonTitle")
    private String lessonTitle;
    @SerializedName("LessonToken")
    private String lessonToken;
    @SerializedName("RowLevel_ID")
    private String rowLevelID;
    @SerializedName("Subject_ID")
    private String subjectID;
    @SerializedName("TeacherName")
    private String teacherName;
    @SerializedName("Level_Name")
    private String levelName;
    @SerializedName("skillsName")
    private String skillsName;

    public String getSkillsName() {
        return skillsName;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getLessonToken() {
        return lessonToken;
    }

    public void setLessonToken(String lessonToken) {
        this.lessonToken = lessonToken;
    }

    public String getRowLevelID() {
        return rowLevelID;
    }

    public void setRowLevelID(String rowLevelID) {
        this.rowLevelID = rowLevelID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    @Override
    public String toString() {
        return getLessonTitle();
    }
}
