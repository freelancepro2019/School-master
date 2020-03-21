
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentDay<T> {

    @SerializedName("Day")
    private String mDay;
    @SerializedName("Day_data")
    private List<T> mDayData;

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getmDay() {
        return mDay;
    }

    public void setmDay(String mDay) {
        this.mDay = mDay;
    }

    public List<T> getmDayData() {
        return mDayData;
    }

    public void setmDayData(List<T> mDayData) {
        this.mDayData = mDayData;
    }
}
