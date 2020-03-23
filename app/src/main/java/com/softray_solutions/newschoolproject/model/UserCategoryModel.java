package com.softray_solutions.newschoolproject.model;

import java.io.Serializable;

public class UserCategoryModel implements Serializable {
    private String ID;
    private String Name;
    private String JobTitle;

    public UserCategoryModel(String ID, String name, String jobTitle) {
        this.ID = ID;
        Name = name;
        JobTitle = jobTitle;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getJobTitle() {
        return JobTitle;
    }
}
