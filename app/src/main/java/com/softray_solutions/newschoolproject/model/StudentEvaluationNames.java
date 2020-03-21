package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentEvaluationNames {

    @SerializedName("defulatDeg")
    private DefulatDeg mDefulatDeg;
    @SerializedName("studentList")
    private List<StudentList> mStudentList;


    public DefulatDeg getDefulatDeg() {
        return mDefulatDeg;
    }

    public void setDefulatDeg(DefulatDeg defulatDeg) {
        mDefulatDeg = defulatDeg;
    }

    public List<StudentList> getStudentList() {
        return mStudentList;
    }

    public void setStudentList(List<StudentList> studentList) {
        mStudentList = studentList;
    }

}
