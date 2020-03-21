package com.softray_solutions.newschoolproject.model;

import android.graphics.drawable.Drawable;

public class Tools {

    private String title;
    private Drawable icon;


    public Tools(String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }
}
