package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Skill {
    @SerializedName("skillID")
    private String skillId;
    @SerializedName("sikllName")
    private String skillName;


    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

//    public String getConfigSemester() {
//        return mConfigSemester;
//    }
//
//    public void setConfigSemester(String configSemester) {
//        mConfigSemester = configSemester;
//    }
//
//    public String getConfigTerm() {
//        return mConfigTerm;
//    }
//
//    public void setConfigTerm(String configTerm) {
//        mConfigTerm = configTerm;
//    }
//
//    public String getContactID() {
//        return mContactID;
//    }
//
//    public void setContactID(String contactID) {
//        mContactID = contactID;
//    }
//
//    public String getDate() {
//        return mDate;
//    }
//
//    public void setDate(String date) {
//        mDate = date;
//    }
//
//    public String getID() {
//        return mID;
//    }
//
//    public void setID(String iD) {
//        mID = iD;
//    }
//
//    public String getIsActive() {
//        return mIsActive;
//    }
//
//    public void setIsActive(String isActive) {
//        mIsActive = isActive;
//    }
//
//    public String getLevelName() {
//        return mLevelName;
//    }
//
//    public void setLevelName(String levelName) {
//        mLevelName = levelName;
//    }
//
//    public String getName() {
//        return mName;
//    }
//
//    public void setName(String name) {
//        mName = name;
//    }
//
//    public String getRowLevelID() {
//        return mRowLevelID;
//    }
//
//    public void setRowLevelID(String rowLevelID) {
//        mRowLevelID = rowLevelID;
//    }
//
//    public String getRowName() {
//        return mRowName;
//    }
//
//    public void setRowName(String rowName) {
//        mRowName = rowName;
//    }
//
//    public String getSubjectID() {
//        return mSubjectID;
//    }
//
//    public void setSubjectID(String subjectID) {
//        mSubjectID = subjectID;
//    }
//
//    public String getSubjectName() {
//        return mSubjectName;
//    }
//
//    public void setSubjectName(String subjectName) {
//        mSubjectName = subjectName;
//    }
//
//    public String getTermID() {
//        return mTermID;
//    }
//
//    public void setTermID(String termID) {
//        mTermID = termID;
//    }
//
//    public String getToken() {
//        return mToken;
//    }
//
//    public void setToken(String token) {
//        mToken = token;
//    }

    @Override
    public String toString() {
        return getSkillName();
    }
}
