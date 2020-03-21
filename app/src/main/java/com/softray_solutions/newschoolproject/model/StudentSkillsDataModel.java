
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentSkillsDataModel {

    @SerializedName("sikllName")
    private String mSikllName;
    @SerializedName("skillID")
    private String mSkillID;

    public String getSikllName() {
        return mSikllName;
    }

    public void setSikllName(String sikllName) {
        mSikllName = sikllName;
    }

    public String getSkillID() {
        return mSkillID;
    }

    public void setSkillID(String skillID) {
        mSkillID = skillID;
    }

    @Override
    public String toString() {
        return getSikllName();
    }
}
