
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DefulatDeg {

    @SerializedName("HomeDegree")
    private String mHomeDegree;
    @SerializedName("TestMark")
    private String mTestMark;

    public String getHomeDegree() {
        return mHomeDegree;
    }

    public void setHomeDegree(String homeDegree) {
        mHomeDegree = homeDegree;
    }

    public String getTestMark() {
        return mTestMark;
    }

    public void setTestMark(String testMark) {
        mTestMark = testMark;
    }

}
