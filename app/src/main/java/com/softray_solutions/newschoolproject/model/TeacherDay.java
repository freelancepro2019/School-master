
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TeacherDay {

    @SerializedName("Day")
    private String mDay;
    @SerializedName("Day_data")
    private List<TeacherClass> mDayData;

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public List<TeacherClass> getDayData() {
        return mDayData;
    }

    public void setDayData(List<TeacherClass> dayData) {
        mDayData = dayData;
    }

}
