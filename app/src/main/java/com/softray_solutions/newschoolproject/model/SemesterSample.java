package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

public class SemesterSample {

    @SerializedName("Name")
    private String name = "";

    @SerializedName("ID")
    private String id = "";

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
