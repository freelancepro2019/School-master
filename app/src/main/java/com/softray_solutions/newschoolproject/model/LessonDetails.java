package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LessonDetails {

    @SerializedName("Aids")
    private String aids;
    @SerializedName("Aims")
    private String aims;
    @SerializedName("Attachs")
    private String attachs;
    @SerializedName("Content")
    private String content;
    @SerializedName("Date")
    private String date;
    @SerializedName("ID")
    private String iD;
    @SerializedName("Intro")
    private String intro;
    @SerializedName("IsActive")
    private String isActive;
    @SerializedName("IsDergree")
    private String isDergree;
    @SerializedName("Lesson_Title")
    private String lessonTitle;
    @SerializedName("Reviews")
    private String reviews;
    @SerializedName("RowLevel_ID")
    private String rowLevelID;
    @SerializedName("Scripts")
    private String scripts;
    @Expose
    private String skillsID;
    @Expose
    private String startDate;
    @Expose
    private String stratigy;
    @SerializedName("Subject_ID")
    private String subjectID;
    @SerializedName("Teacher_ID")
    private String teacherID;
    @SerializedName("Token")
    private String token;
    @Expose
    private String trainhome;
    @SerializedName("files")
    private List<String> fileAttachmentString;

    public List<String> getFileAttachmentString() {
        return fileAttachmentString;
    }

    public void setFileAttachmentString(List<String> fileAttachmentString) {
        this.fileAttachmentString = fileAttachmentString;
    }

    public String getAids() {
        return aids;
    }

    public void setAids(String aids) {
        this.aids = aids;
    }

    public String getAims() {
        return aims;
    }

    public void setAims(String aims) {
        this.aims = aims;
    }

    public String getAttachs() {
        return attachs;
    }

    public void setAttachs(String attachs) {
        this.attachs = attachs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsDergree() {
        return isDergree;
    }

    public void setIsDergree(String isDergree) {
        this.isDergree = isDergree;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getRowLevelID() {
        return rowLevelID;
    }

    public void setRowLevelID(String rowLevelID) {
        this.rowLevelID = rowLevelID;
    }

    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }

    public String getSkillsID() {
        return skillsID;
    }

    public void setSkillsID(String skillsID) {
        this.skillsID = skillsID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStratigy() {
        return stratigy;
    }

    public void setStratigy(String stratigy) {
        this.stratigy = stratigy;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTrainhome() {
        return trainhome;
    }

    public void setTrainhome(String trainhome) {
        this.trainhome = trainhome;
    }

}
