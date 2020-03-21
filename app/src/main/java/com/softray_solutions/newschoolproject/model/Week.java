package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

public class Week {

    @SerializedName("ID")
    private String id = "";

    @SerializedName("Name")
    private String name = "";

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
