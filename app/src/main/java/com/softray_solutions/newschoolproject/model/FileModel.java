package com.softray_solutions.newschoolproject.model;

import java.io.Serializable;

public class FileModel implements Serializable {

    private String name;
    private String size;
    private String path;

    public FileModel(String name, String size, String path) {
        this.name = name;
        this.size = size;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }
}
