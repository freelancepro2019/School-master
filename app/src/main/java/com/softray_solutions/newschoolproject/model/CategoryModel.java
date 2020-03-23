package com.softray_solutions.newschoolproject.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String name;
    private String value;

    public CategoryModel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
