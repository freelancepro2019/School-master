
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentClass {

    @SerializedName("Object")
    private String mObject;
    @SerializedName("teach")
    private String mTeach;

    public StudentClass(String mObject, String mTeach) {
        this.mObject = mObject;
        this.mTeach = mTeach;
    }

    public String getObject() {
        return mObject;
    }

    public void setObject(String object) {
        mObject = object;
    }

    public String getTeach() {
        return mTeach;
    }

    public void setTeach(String teach) {
        mTeach = teach;
    }

}
