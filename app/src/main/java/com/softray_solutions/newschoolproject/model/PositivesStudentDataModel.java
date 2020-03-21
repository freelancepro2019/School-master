
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PositivesStudentDataModel {

    @SerializedName("PositiveID")
    private String mPositiveID;
    @SerializedName("PositiveName")
    private String mPositiveName;

    public String getPositiveID() {
        return mPositiveID;
    }

    public void setPositiveID(String positiveID) {
        mPositiveID = positiveID;
    }

    public String getPositiveName() {
        return mPositiveName;
    }

    public void setPositiveName(String positiveName) {
        mPositiveName = positiveName;
    }

    @Override
    public String toString() {
        return getPositiveName();
    }
}
